package com.example.communify.domain.repositories

interface DetailsRepository {
    suspend fun sendEmail(message: String, email: String)
    suspend fun makeCall(phoneNumber: String)
    suspend fun openMap(address: String)
}