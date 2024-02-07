package com.example.communify.domain.details

interface DetailsInteractor {
    suspend fun sendEmail(message: String)
    suspend fun makeCall(phoneNumber: String)
    suspend fun openMap(address: String)
}