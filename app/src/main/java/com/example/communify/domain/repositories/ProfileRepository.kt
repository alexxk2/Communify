package com.example.communify.domain.repositories

import com.example.communify.domain.models.Credentials

interface ProfileRepository {

    suspend fun changeName(userCredentials: Credentials)
    suspend fun changeProfileImage(userCredentials: Credentials)
    suspend fun changeLogin(userCredentials: Credentials)
    suspend fun changePassword(userCredentials: Credentials)
    suspend fun deleteAccount(userCredentials: Credentials)

}