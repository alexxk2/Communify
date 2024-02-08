package com.example.communify.domain.login

import com.example.communify.domain.models.AuthResult
import com.example.communify.domain.models.Credentials

interface LoginInteractor {

    suspend fun login(login: String, password: String): AuthResult
    suspend fun signIn(userCredentials: Credentials): AuthResult
    suspend fun guestEntrance()
}