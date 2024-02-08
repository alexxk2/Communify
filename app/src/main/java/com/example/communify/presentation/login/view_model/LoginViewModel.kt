package com.example.communify.presentation.login.view_model

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communify.domain.login.LoginInteractor
import com.example.communify.domain.models.AuthResult
import com.example.communify.domain.models.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
) : ViewModel() {

    private val _isLoginScreen = MutableLiveData<Boolean>()
    val isLoginScreen: LiveData<Boolean> = _isLoginScreen

    private val _isAllFieldsValid = MutableLiveData(false)
    val isAllFieldsValid: LiveData<Boolean> = _isAllFieldsValid

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult


    fun login(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authResult.postValue(loginInteractor.login(login, password))
        }
    }

    fun signIn(
        name: String,
        login: String,
        password: String
        ){
        viewModelScope.launch(Dispatchers.IO) {

            val uniqueKey = "${login}${(1..10000).random()}"
            val credentials = Credentials(
                uniqueKey = uniqueKey,
                name = name,
                login = login,
                password = password,
                userImage = null
            )
            _authResult.postValue(loginInteractor.signIn(credentials))
        }
    }

    fun isAllInputsNotEmpty(
        firstName: Editable?,
        login: Editable?,
        password: Editable?
    ){
        _isAllFieldsValid.value = firstName.toString().isNotBlank() && login.toString().isNotBlank() && password.toString().isNotBlank()
    }

    fun isAllInputsNotEmpty(
        login: Editable?,
        password: Editable?
    ){
        _isAllFieldsValid.value = login.toString().isNotBlank() && password.toString().isNotBlank()
    }

    fun setScreenState(isLogin: Boolean){
        _isLoginScreen.postValue(isLogin)
    }


}