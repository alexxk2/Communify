package com.example.communify.domain.profile

import android.net.Uri
import com.example.communify.domain.models.Credentials
import com.example.communify.domain.repositories.ContactsRepository
import com.example.communify.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileInteractorImpl @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val contactsRepository: ContactsRepository
) : ProfileInteractor {


    override suspend fun updateProfile(userCredentials: Credentials) {
        profileRepository.updateProfile(userCredentials)
    }

    override suspend fun deleteAccount(userCredentials: Credentials) {
        profileRepository.deleteAccount(userCredentials)
    }

    override suspend fun getProfile(): Credentials? {
       return profileRepository.getProfile()
    }

    override suspend fun saveImageAndReturnPath(uri: Uri): Uri {
        return profileRepository.saveImageAndReturnPath(uri)
    }

    override suspend fun deleteOldContacts(uniqueKey: String) {
        contactsRepository.deleteContacts(uniqueKey)
    }
}