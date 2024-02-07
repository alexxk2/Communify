package com.example.communify.data.network

import com.example.communify.data.network.dto.ContactDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("/api/0.8")
    suspend fun getUsers(@Query("results") results: Int = 15): Response<List<ContactDto>>
}