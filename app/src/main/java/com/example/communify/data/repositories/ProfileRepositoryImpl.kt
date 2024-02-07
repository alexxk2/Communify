package com.example.communify.data.repositories

import com.example.communify.data.converters.DBConverter
import com.example.communify.data.db.ContactsDao
import com.example.communify.domain.models.Credentials
import com.example.communify.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val databaseDBConverter: DBConverter,
    private val contactsDao: ContactsDao

) : ProfileRepository {


    override suspend fun changeName(userCredentials: Credentials) {
        TODO("Not yet implemented")
    }

    override suspend fun changeProfileImage(userCredentials: Credentials) {
        TODO("Not yet implemented")
    }

    override suspend fun changeLogin(userCredentials: Credentials) {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(userCredentials: Credentials) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(userCredentials: Credentials) {
        TODO("Not yet implemented")
    }
}