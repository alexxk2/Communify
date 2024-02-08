package com.example.communify.presentation.contacts.models

import com.example.communify.domain.models.Contact

sealed interface ContactsScreenState {
    data object Loading: ContactsScreenState
    data object Error: ContactsScreenState
    data object Empty: ContactsScreenState
    data class Content(val contacts: List<Contact>): ContactsScreenState
}