package com.example.communify.data.db.impl

import com.example.communify.data.db.ContactsDao
import com.example.communify.data.db.CredentialsDao
import com.example.communify.data.db.RoomStorage
import com.example.communify.data.db.dto.CredentialsEntity
import com.example.communify.data.db.dto.UserEntity
import javax.inject.Inject

class RoomStorageImpl @Inject constructor(
    private val contactsDao: ContactsDao,
    private val credentialsDao: CredentialsDao
) : RoomStorage {

    override suspend fun getAllContactsStorage(uniqueKey: String): List<UserEntity> {
        return contactsDao.getAllContactsStorage(uniqueKey)
    }

    override suspend fun addAllContacts(contacts: List<UserEntity>) {
        contactsDao.addAllContacts(contacts)
    }

    override suspend fun getCredentials(login: String): CredentialsEntity {
        return credentialsDao.getCredentials(login)
    }

    override suspend fun addCredentials(userCredentials: CredentialsEntity) {
        credentialsDao.addCredentials(userCredentials)
    }

    override suspend fun changeName(userCredentials: CredentialsEntity) {
        credentialsDao.changeName(userCredentials)
    }

    override suspend fun changeProfileImage(userCredentials: CredentialsEntity) {
        credentialsDao.changeProfileImage(userCredentials)
    }

    override suspend fun changeLogin(userCredentials: CredentialsEntity) {
        credentialsDao.changeLogin(userCredentials)
    }

    override suspend fun changePassword(userCredentials: CredentialsEntity) {
        credentialsDao.changePassword(userCredentials)
    }

    override suspend fun deleteCredentials(userCredentials: CredentialsEntity) {
        credentialsDao.deleteCredentials(userCredentials)
    }
}