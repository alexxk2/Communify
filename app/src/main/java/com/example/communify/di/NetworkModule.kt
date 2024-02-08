package com.example.communify.di

import com.example.communify.data.converters.DBConverter
import com.example.communify.data.converters.NetworkConverter
import com.example.communify.data.db.ContactsDao
import com.example.communify.data.network.NetworkClient
import com.example.communify.data.network.RandomUserApi
import com.example.communify.data.network.impl.NetworkClientImpl
import com.example.communify.data.repositories.ContactsRepositoryImpl
import com.example.communify.data.sp_storage.SPStorage
import com.example.communify.domain.repositories.ContactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val baseUrl = "https://randomuser.me"
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideRandomUserApi(retrofit: Retrofit): RandomUserApi {
        return retrofit.create(RandomUserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkClient(randomUserApi: RandomUserApi): NetworkClient {
        return NetworkClientImpl(randomUserApi)
    }

    @Provides
    @Singleton
    fun provideContactsRepository(
        networkClient: NetworkClient,
        networkConverter: NetworkConverter,
        contactsDao: ContactsDao,
        spStorage: SPStorage,
        dbConverter: DBConverter
    ): ContactsRepository {
        return ContactsRepositoryImpl(
            networkClient,
            networkConverter,
            contactsDao,
            spStorage,
            dbConverter
        )
    }

}