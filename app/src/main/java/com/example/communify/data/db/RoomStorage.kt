package com.example.communify.data.db

import com.example.communify.data.db.dto.CredentialsEntity
import com.example.communify.data.db.dto.UserEntity

interface RoomStorage {

    suspend fun getAllContactsStorage(uniqueKey: String): List<UserEntity>
    suspend fun addAllContacts(contacts: List<UserEntity>)

    suspend fun getCredentials(login: String): CredentialsEntity
    suspend fun addCredentials(userCredentials: CredentialsEntity)


    suspend fun changeName(userCredentials: CredentialsEntity)
    suspend fun changeProfileImage(userCredentials: CredentialsEntity)
    suspend fun changeLogin(userCredentials: CredentialsEntity)
    suspend fun changePassword(userCredentials: CredentialsEntity)
    suspend fun deleteCredentials(userCredentials: CredentialsEntity)
}