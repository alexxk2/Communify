package com.example.communify.domain.contacts

import com.example.communify.domain.models.Contact

interface ContactsInteractor {
    suspend fun getAllContactsApi(): List<Contact>
    suspend fun getAllContactsStorage(): List<Contact>
    suspend fun isStorageEmpty(): Boolean
    suspend fun filterContacts(filterWord: String): List<Contact>
}