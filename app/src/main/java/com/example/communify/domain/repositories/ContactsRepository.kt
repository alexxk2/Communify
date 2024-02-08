package com.example.communify.domain.repositories

import com.example.communify.domain.models.Contact

interface ContactsRepository {

    suspend fun getAllContactsApi(): List<Contact>
    suspend fun getAllContactsStorage(): List<Contact>
    suspend fun deleteContacts(uniqueKey: String)
}