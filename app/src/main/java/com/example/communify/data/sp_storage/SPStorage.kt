package com.example.communify.data.sp_storage


interface SPStorage {

    suspend fun getUniqueUserKey(): String
    suspend fun putUniqueUserKey(uniqueKey: String)
}