package com.example.communify.domain.start

import com.example.communify.domain.repositories.LoginRepository
import javax.inject.Inject

class StartInteractorImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : StartInteractor {

    override suspend fun guestEntrance() {
        loginRepository.guestEntrance()
    }
}