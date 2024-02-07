package com.example.communify.domain.repositories

import com.example.communify.domain.models.Credentials
import com.example.communify.domain.models.AuthResult

interface LoginRepository {

    suspend fun login(login: String, password: String): AuthResult
    suspend fun signIn(userCredentials: Credentials): AuthResult
    suspend fun guestEntrance()

}