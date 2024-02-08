package com.example.communify.presentation.contacts.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communify.domain.contacts.ContactsInteractor
import com.example.communify.domain.details.DetailsInteractor
import com.example.communify.domain.models.Contact
import com.example.communify.presentation.contacts.models.ContactsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsInteractor: ContactsInteractor,
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {

    private lateinit var defaultContactsList: List<Contact>

    private val _contactsScreenState = MutableLiveData<ContactsScreenState>()
    val contactsScreenState: LiveData<ContactsScreenState> = _contactsScreenState


    fun getContactsFromApi() {
        _contactsScreenState.postValue(ContactsScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultFromApi = contactsInteractor.getAllContactsApi()

                _contactsScreenState.postValue(
                    if (resultFromApi.isEmpty()) {
                        ContactsScreenState.Error
                    } else {
                        defaultContactsList = resultFromApi
                        ContactsScreenState.Content(resultFromApi)
                    }
                )
            } catch (e: Exception) {
                _contactsScreenState.postValue(ContactsScreenState.Error)
            }
        }
    }

    fun getContactsFromDB() {
        _contactsScreenState.postValue(ContactsScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultFromDB = contactsInteractor.getAllContactsStorage()

                _contactsScreenState.postValue(
                    if (resultFromDB.isEmpty()) {
                        ContactsScreenState.Error
                    } else {
                        defaultContactsList = resultFromDB
                        ContactsScreenState.Content(resultFromDB)
                    }
                )
            } catch (e: Exception) {
                _contactsScreenState.postValue(ContactsScreenState.Error)
            }
        }
    }

    fun filterContacts(filterWord: String) {
        _contactsScreenState.postValue(ContactsScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {

            val resultFromDomain =
                contactsInteractor.filterContacts(defaultContactsList, filterWord)

            _contactsScreenState.postValue(
                if (resultFromDomain.isEmpty()) {
                    ContactsScreenState.Empty
                } else {
                    ContactsScreenState.Content(resultFromDomain)
                }
            )
        }
    }

    fun makeFilterDefault() {
        _contactsScreenState.postValue(ContactsScreenState.Content(defaultContactsList))
    }

    fun sendMessage(message: String, email: String){
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.sendEmail(message,email)

        }
    }

    fun makeADial(phoneNumber: String){
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.makeCall(phoneNumber)
        }
    }

}