package com.example.communify.domain.contacts

import com.example.communify.domain.models.Contact

interface ContactsInteractor {
    suspend fun getAllContactsApi(): List<Contact>
    suspend fun getAllContactsStorage(): List<Contact>
    suspend fun filterContacts(defaultList: List<Contact>, filterWord: String): List<Contact>
}