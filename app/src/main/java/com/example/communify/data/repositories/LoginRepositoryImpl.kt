package com.example.communify.data.repositories

import com.example.communify.data.converters.DBConverter
import com.example.communify.data.db.CredentialsDao
import com.example.communify.data.sp_storage.SPStorage
import com.example.communify.domain.models.AuthResult
import com.example.communify.domain.models.Credentials
import com.example.communify.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val databaseDBConverter: DBConverter,
    private val credentialsDao: CredentialsDao,
    private val spStorage: SPStorage
) : LoginRepository {


    override suspend fun login(login: String, password: String): AuthResult {

        val resultFromDb = credentialsDao.getCredentials(login)

        return if (resultFromDb == null) {
            AuthResult.Failure(message = "Пользователь с таким логином не найден")
        } else if (resultFromDb.password != password) {

            AuthResult.Failure(message = "Неверный пароль")
        } else {
            spStorage.putUniqueUserKey(resultFromDb.uniqueKey)
            AuthResult.Success
        }

    }

    override suspend fun signIn(userCredentials: Credentials): AuthResult {

        val resultFromDb = credentialsDao.getCredentials(userCredentials.login)

        return if (resultFromDb != null) {
            AuthResult.Failure(message = "Пользователь с таким логином уже существует")
        } else {
            credentialsDao.addCredentials(databaseDBConverter.convert(userCredentials))
            spStorage.putUniqueUserKey(userCredentials.uniqueKey)
            AuthResult.Success
        }
    }

    override suspend fun guestEntrance() {
        spStorage.putUniqueUserKey("")
    }
}