package com.example.communify.presentation.details.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communify.domain.details.DetailsInteractor
import com.example.communify.domain.models.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsInteractor: DetailsInteractor
): ViewModel() {

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact> = _contact



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

    fun openMap(address: String){
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.openMap(address)
        }
    }


    fun setContact(contact: Contact){
        _contact.value = contact
    }


}