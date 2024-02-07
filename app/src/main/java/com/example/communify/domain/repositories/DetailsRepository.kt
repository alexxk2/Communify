package com.example.communify.domain.repositories

import android.location.GnssNavigationMessage

interface DetailsRepository {
    suspend fun sendEmail(message: String)
    suspend fun makeCall(phoneNumber: String)
    suspend fun openMap(address: String)
}