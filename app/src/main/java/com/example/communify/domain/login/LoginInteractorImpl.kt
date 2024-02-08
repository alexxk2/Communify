package com.example.communify.domain.login

import com.example.communify.domain.models.AuthResult
import com.example.communify.domain.models.Credentials
import com.example.communify.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : LoginInteractor {

    override suspend fun login(login: String, password: String): AuthResult {
        return loginRepository.login(login, password)
    }

    override suspend fun signIn(userCredentials: Credentials): AuthResult {
        return loginRepository.signIn(userCredentials)
    }

    override suspend fun guestEntrance() {
        loginRepository.guestEntrance()
    }
}