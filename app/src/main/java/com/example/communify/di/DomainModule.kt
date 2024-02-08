package com.example.communify.di

import com.example.communify.domain.contacts.ContactsInteractor
import com.example.communify.domain.contacts.ContactsInteractorImpl
import com.example.communify.domain.details.DetailsInteractor
import com.example.communify.domain.details.DetailsInteractorImpl
import com.example.communify.domain.login.LoginInteractor
import com.example.communify.domain.login.LoginInteractorImpl
import com.example.communify.domain.profile.ProfileInteractor
import com.example.communify.domain.profile.ProfileInteractorImpl
import com.example.communify.domain.repositories.ContactsRepository
import com.example.communify.domain.repositories.DetailsRepository
import com.example.communify.domain.repositories.LoginRepository
import com.example.communify.domain.repositories.ProfileRepository
import com.example.communify.domain.start.StartInteractor
import com.example.communify.domain.start.StartInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {


    @Provides
    fun provideContactsInteractor(contactsRepository: ContactsRepository): ContactsInteractor {
        return ContactsInteractorImpl(contactsRepository)
    }

    @Provides
    fun provideDetailsInteractor(detailsRepository: DetailsRepository): DetailsInteractor {
        return DetailsInteractorImpl(detailsRepository)
    }

    @Provides
    fun provideLoginInteractor(loginRepository: LoginRepository): LoginInteractor {
        return LoginInteractorImpl(loginRepository)
    }

    @Provides
    fun provideProfileInteractor(profileRepository: ProfileRepository, contactsRepository: ContactsRepository): ProfileInteractor {
        return ProfileInteractorImpl(profileRepository, contactsRepository)
    }

    @Provides
    fun provideStartInteractor(loginRepository: LoginRepository): StartInteractor {
        return StartInteractorImpl(loginRepository)
    }



}