package com.example.communify.di

import android.content.Context
import androidx.room.Room
import com.example.communify.data.converters.DBConverter
import com.example.communify.data.db.AppDatabase
import com.example.communify.data.db.ContactsDao
import com.example.communify.data.db.CredentialsDao
import com.example.communify.data.media_storage.ImageSaver
import com.example.communify.data.media_storage.impl.ImageSaverImpl
import com.example.communify.data.repositories.DetailsRepositoryImpl
import com.example.communify.data.repositories.LoginRepositoryImpl
import com.example.communify.data.repositories.ProfileRepositoryImpl
import com.example.communify.data.sp_storage.SPStorage
import com.example.communify.data.sp_storage.SPStorageImpl
import com.example.communify.domain.repositories.DetailsRepository
import com.example.communify.domain.repositories.LoginRepository
import com.example.communify.domain.repositories.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideContactsDao(appDatabase: AppDatabase): ContactsDao {
        return appDatabase.contactsDao()
    }

    @Provides
    @Singleton
    fun provideCredentialsDao(appDatabase: AppDatabase): CredentialsDao {
        return appDatabase.credentialsDao()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        databaseConverter: DBConverter,
        credentialsDao: CredentialsDao,
        spStorage: SPStorage
    ): LoginRepository {
        return LoginRepositoryImpl(databaseConverter, credentialsDao, spStorage)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        databaseConverter: DBConverter,
        credentialsDao: CredentialsDao,
        spStorage: SPStorage,
        imageSaver: ImageSaver
    ): ProfileRepository {
        return ProfileRepositoryImpl(databaseConverter, credentialsDao, spStorage, imageSaver)
    }

    @Provides
    @Singleton
    fun provideSPStorage(@ApplicationContext context: Context): SPStorage {
        return SPStorageImpl(context)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(@ApplicationContext context: Context): DetailsRepository{
        return DetailsRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideImageSaver(@ApplicationContext context: Context): ImageSaver{
        return ImageSaverImpl(context)
    }

    companion object {
        const val DATABASE = "app_database"
    }
}