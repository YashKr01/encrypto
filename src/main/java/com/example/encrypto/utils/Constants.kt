package com.example.encrypto.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.material.snackbar.Snackbar

object Constants {

    // for selecting image
    const val REQUEST_CODE = 123

    // for cryptography
    const val ALGORITHM = "SHA-256"

    // firebase keys
    const val DATABASE_USERS_KEY = "users"
    const val PROFILE_IMAGE_KEY = "uploads"
    const val DATABASE_MESSAGES_KEY = "messages"
    const val IMAGES_KEY = "message_images"
    const val DATABASE_USER_CHATS = "user_chats"
    const val BASE_URL = "https://fcm.googleapis.com/fcm/"

    // for intent
    const val USERNAME = "username"
    const val PROFILE_IMAGE = "profile_image"
    const val USER_ID = "user_id"
    const val USER = "user"

    fun showSnackBar(context: Context, message: String, view: View) {
        Snackbar.make(context, view, message, Snackbar.LENGTH_SHORT).show()
    }

}