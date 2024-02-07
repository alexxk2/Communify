package com.example.communify.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.communify.data.db.dto.CredentialsEntity

interface CredentialsDao {

    @Query("SELECT * FROM credentials WHERE login=:login")
    suspend fun getCredentials(login: String): CredentialsEntity?


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCredentials(userCredentials: CredentialsEntity)


    @Update
    suspend fun changeName(userCredentials: CredentialsEntity)
    @Update
    suspend fun changeProfileImage(userCredentials: CredentialsEntity)
    @Update
    suspend fun changeLogin(userCredentials: CredentialsEntity)
    @Update
    suspend fun changePassword(userCredentials: CredentialsEntity)

    @Delete
    suspend fun deleteCredentials(userCredentials: CredentialsEntity)
}