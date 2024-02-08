package com.example.communify.domain.details

import com.example.communify.domain.repositories.DetailsRepository
import javax.inject.Inject

class DetailsInteractorImpl @Inject constructor(private val detailsRepository: DetailsRepository) :
    DetailsInteractor {


    override suspend fun sendEmail(message: String, email: String) {
        detailsRepository.sendEmail(message, email)
    }

    override suspend fun makeCall(phoneNumber: String) {
        detailsRepository.makeCall(phoneNumber)
    }

    override suspend fun openMap(address: String) {
        detailsRepository.openMap(address)
    }
}