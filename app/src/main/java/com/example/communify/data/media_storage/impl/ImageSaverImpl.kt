package com.example.communify.data.media_storage.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.example.communify.data.media_storage.ImageSaver
import com.example.communify.data.sp_storage.SPStorageImpl.Companion.SHARED_PREFS
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageSaverImpl @Inject constructor(private val context: Context) : ImageSaver {

    private var coverCount = 0
    private var coverSrc = "-1"
    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, 0)


    override suspend fun saveImageAndReturnPath(uri: Uri): Uri {

        coverCount = sharedPrefs.getInt(COVERS_COUNT, 0)

        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), COVERS)
        //create catalog if not exist
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        coverSrc = "cover_${coverCount}.jpg"
        val file = File(filePath, coverSrc)
        coverCount++
        putCountInSharedPrefs(coverCount)

        val inputStream = context.contentResolver.openInputStream(uri)
        val outPutStream = FileOutputStream(file)

        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outPutStream)

        return file.toUri()
    }

    private fun putCountInSharedPrefs(count: Int) {
        sharedPrefs.edit()
            .putInt(COVERS_COUNT, count)
            .apply()
    }

    companion object {
        private const val COVERS = "covers"
        private const val COVERS_COUNT = "covers_count"

    }
}