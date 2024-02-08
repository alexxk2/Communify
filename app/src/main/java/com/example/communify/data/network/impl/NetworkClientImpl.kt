package com.example.communify.data.network.impl

import com.example.communify.data.network.NetworkClient
import com.example.communify.data.network.RandomUserApi
import com.example.communify.data.network.dto.ContactDto
import javax.inject.Inject

class NetworkClientImpl @Inject constructor(private val randomUserApi: RandomUserApi) :
    NetworkClient {

    override suspend fun getUsers(): List<ContactDto> {
        val response = randomUserApi.getUsers()
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }
}