package com.example.communify.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.communify.data.db.dto.UserEntity

@Dao
interface ContactsDao {

    @Query("SELECT * FROM users WHERE unique_key=:uniqueKey")
    suspend fun getAllContactsStorage(uniqueKey: String): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllContacts(contacts: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearContacts()

}