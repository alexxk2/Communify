package com.example.communify.presentation.profile.view_model


import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communify.domain.models.Credentials
import com.example.communify.domain.profile.ProfileInteractor
import com.example.communify.presentation.profile.models.ProfileScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor
) : ViewModel() {

    private val _profileScreenState = MutableLiveData<ProfileScreenState>()
    val profileScreenState: LiveData<ProfileScreenState> = _profileScreenState

    private val _avatarSrc = MutableLiveData<Uri?>(null)
    val avatarSrc: LiveData<Uri?> = _avatarSrc

    private val _isUpdateAvailable = MutableLiveData<Boolean>()
    val isUpdateAvailable: LiveData<Boolean> = _isUpdateAvailable

    private lateinit var currentProfile: Credentials

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {

            val profile = profileInteractor.getProfile()
            _profileScreenState.postValue(
                if (profile == null) {
                    ProfileScreenState.Unauthorized
                } else {
                    currentProfile = profile
                    ProfileScreenState.Authorized(profile)
                }
            )
        }
    }

    fun saveImageToPrivateStorage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _avatarSrc.postValue(profileInteractor.saveImageAndReturnPath(uri))
            updateProfile(currentProfile.login, currentProfile.password, uri)
        }
    }

    fun updateProfile(login: String, password: String, uri: Uri? =_avatarSrc.value ) {
        val newCredentials = currentProfile.copy(
            login = login,
            password = password,
            userImage = uri?:currentProfile.userImage,
        )

        currentProfile = newCredentials
        viewModelScope.launch(Dispatchers.IO) {
            profileInteractor.updateProfile(newCredentials)
        }
    }

    fun validateChangesForUpdate(login: String, password: String) {

        val isInfoNew = login != currentProfile.login || password != currentProfile.password
        val isValuesNotBlank = login.isNotBlank() && password.isNotBlank()
        _isUpdateAvailable.value = isValuesNotBlank && isInfoNew

    }

    fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            profileInteractor.deleteAccount(currentProfile)
            profileInteractor.deleteOldContacts(currentProfile.uniqueKey)
        }
    }


}