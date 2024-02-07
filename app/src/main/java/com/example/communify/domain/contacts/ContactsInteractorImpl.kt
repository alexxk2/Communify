package com.example.communify.domain.contacts

import com.example.communify.domain.models.Contact

class ContactsInteractorImpl : ContactsInteractor {

    override suspend fun getAllContactsApi(): List<Contact> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllContactsStorage(): List<Contact> {
        TODO("Not yet implemented")
    }

    override suspend fun isStorageEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun filterContacts(filterWord: String): List<Contact> {
        TODO("Not yet implemented")
    }
}