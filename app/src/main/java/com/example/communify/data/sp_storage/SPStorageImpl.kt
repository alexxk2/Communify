package com.example.communify.data.sp_storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


class SPStorageImpl @Inject constructor(
    private val context: Context
) : SPStorage {

    private lateinit var sharedPrefs: SharedPreferences

    override suspend fun getUniqueUserKey(): String {
        sharedPrefs = context.getSharedPreferences(SHARED_PREFS, 0)
        return sharedPrefs.getString(USERS_UNIQUE_ID, "") ?: ""
    }


    override suspend fun putUniqueUserKey(uniqueKey: String) {
        sharedPrefs = context.getSharedPreferences(SHARED_PREFS, 0)
        sharedPrefs.edit().putString(USERS_UNIQUE_ID, uniqueKey).apply()
    }


    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val USERS_UNIQUE_ID = "users_unique_id"
    }
}