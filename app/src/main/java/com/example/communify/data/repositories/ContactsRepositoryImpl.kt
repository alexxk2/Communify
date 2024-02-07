package com.example.communify.data.repositories

import android.util.Log
import com.example.communify.data.converters.DBConverter
import com.example.communify.data.converters.NetworkConverter
import com.example.communify.data.db.ContactsDao
import com.example.communify.data.network.NetworkClient
import com.example.communify.data.sp_storage.SPStorage
import com.example.communify.domain.models.Contact
import com.example.communify.domain.repositories.ContactsRepository
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val networkConverter: NetworkConverter,
    private val contactsDao: ContactsDao,
    private val spStorage: SPStorage,
    private val dbConverter: DBConverter

) :
    ContactsRepository {

    //реализовать запрос в SP на получение ключа пользователя, чтобы добавить его к файлам

    override suspend fun getAllContactsApi(): List<Contact> {
        val uniqueKey = spStorage.getUniqueUserKey()

        return try {
            val resultFromApi = networkClient.getUsers()

            if (resultFromApi.isEmpty()) {
                emptyList()
            } else {
                contactsDao.addAllContacts(
                    resultFromApi.map {
                        dbConverter.convert(it)
                    }
                )
                resultFromApi.map {
                    networkConverter.convert(it, uniqueKey)
                }
            }
        } catch (e: Exception) {
            Log.d("my_tag", e.localizedMessage ?: "Problem with getting users from Api")
            emptyList()
        }
    }


    override suspend fun getAllContactsStorage(): List<Contact> {

        val uniqueKey = spStorage.getUniqueUserKey()

        return try {
            val result = contactsDao.getAllContactsStorage(uniqueKey)
            if (result.isEmpty()) {
                getAllContactsApi()
            } else {
                result.map {
                    dbConverter.convert(it)
                }
            }

        } catch (e: Exception) {
            Log.d("my_tag", e.localizedMessage ?: "Problem with getting users from DB")
            emptyList()
        }
    }

}