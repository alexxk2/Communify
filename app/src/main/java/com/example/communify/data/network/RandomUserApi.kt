package com.example.communify.data.network

import com.example.communify.data.network.dto.ContactsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("/api/0.8")
    suspend fun getUsers(
        @Query("results") results: Int = 15,
        @Query("nat") nationality: String ="us"

    ): Response<ContactsResponse>
}