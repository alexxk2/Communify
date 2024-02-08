package com.example.communify.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.communify.data.db.dto.CredentialsEntity

@Dao
interface CredentialsDao {

    @Query("SELECT * FROM credentials WHERE login=:login")
    suspend fun getCredentials(login: String): CredentialsEntity?

    @Query("SELECT * FROM credentials WHERE unique_key=:uniqueKey")
    suspend fun getCredentialsKey(uniqueKey: String): CredentialsEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCredentials(userCredentials: CredentialsEntity)

    @Update
    suspend fun updateProfile(userCredentials: CredentialsEntity)

    @Delete
    suspend fun deleteCredentials(userCredentials: CredentialsEntity)
}