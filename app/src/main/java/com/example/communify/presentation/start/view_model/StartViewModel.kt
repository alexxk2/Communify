package com.example.communify.presentation.start.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communify.domain.login.LoginInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
): ViewModel() {

    fun guestEntrance(){
        viewModelScope.launch(Dispatchers.IO) {
            loginInteractor.guestEntrance()
        }
    }
}