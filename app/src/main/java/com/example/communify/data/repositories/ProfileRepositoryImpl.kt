package com.example.communify.data.repositories

import android.net.Uri
import com.example.communify.data.converters.DBConverter
import com.example.communify.data.db.CredentialsDao
import com.example.communify.data.media_storage.ImageSaver
import com.example.communify.data.sp_storage.SPStorage
import com.example.communify.domain.models.Credentials
import com.example.communify.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dbConverter: DBConverter,
    private val credentialsDao: CredentialsDao,
    private val spStorage: SPStorage,
    private val imageSaver: ImageSaver

) : ProfileRepository {


    override suspend fun updateProfile(userCredentials: Credentials) {
        credentialsDao.updateProfile(dbConverter.convert(userCredentials))
    }

    override suspend fun deleteAccount(userCredentials: Credentials) {
        credentialsDao.deleteCredentials(dbConverter.convert(userCredentials))
    }

    override suspend fun getProfile(): Credentials? {
        val uniqueKey = spStorage.getUniqueUserKey()
        return if (uniqueKey.isEmpty()) {
            null
        } else {
            val resultFromDb = credentialsDao.getCredentialsKey(uniqueKey) ?: return null
            dbConverter.convert(resultFromDb)
        }
    }

    override suspend fun saveImageAndReturnPath(uri: Uri): Uri  = imageSaver.saveImageAndReturnPath(uri)
}