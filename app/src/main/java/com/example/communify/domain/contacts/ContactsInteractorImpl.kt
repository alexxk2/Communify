package com.example.communify.domain.contacts

import com.example.communify.domain.models.Contact
import com.example.communify.domain.repositories.ContactsRepository
import javax.inject.Inject

class ContactsInteractorImpl @Inject constructor (
    private val contactsRepository: ContactsRepository
): ContactsInteractor {


    override suspend fun getAllContactsApi(): List<Contact> {
       return contactsRepository.getAllContactsApi()
    }

    override suspend fun getAllContactsStorage(): List<Contact> {
        return contactsRepository.getAllContactsStorage()
    }

    override suspend fun filterContacts(
        defaultList: List<Contact>,
        filterWord: String
    ): List<Contact> {
        return defaultList.filter {
            it.user.gender == filterWord
        }
    }
}