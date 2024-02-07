package com.example.communify.data.network

import com.example.communify.data.network.dto.ContactDto

interface NetworkClient {

    suspend fun getUsers(): List<ContactDto>
}