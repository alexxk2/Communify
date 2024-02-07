package com.example.communify.data.repositories

import com.example.communify.domain.repositories.DetailsRepository

class DetailsRepositoryImpl : DetailsRepository {

    override suspend fun sendEmail(message: String) {
        TODO("Not yet implemented")
    }

    override suspend fun makeCall(phoneNumber: String) {
        TODO("Not yet implemented")
    }

    override suspend fun openMap(address: String) {
        TODO("Not yet implemented")
    }
}