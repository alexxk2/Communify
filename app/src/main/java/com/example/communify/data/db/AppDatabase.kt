package com.example.communify.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.communify.data.db.dto.CredentialsEntity
import com.example.communify.data.db.dto.UserEntity

@Database(
    entities = [CredentialsEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    abstract fun credentialsDao(): CredentialsDao
}