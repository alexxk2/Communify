package com.example.communify.data.repositories

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.communify.R
import com.example.communify.domain.repositories.DetailsRepository
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(private val context: Context) : DetailsRepository {

    override suspend fun sendEmail(message: String, email: String) {

        val messageTitle = context.getString(R.string.message_title)

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_SUBJECT, messageTitle)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }

    override suspend fun makeCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phoneNumber}")
        //intent.putExtra(Intent.EXTRA_PHONE_NUMBER, phoneNumber)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(context, intent, null)
    }

    override suspend fun openMap(address: String) {
        val gmmIntentUri =
            Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(context, mapIntent, null)

    }
}