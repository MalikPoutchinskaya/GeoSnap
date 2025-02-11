package com.kayakstudio.geosnap.tools.android

import android.content.Context
import android.content.Intent

class AndroidOpenExternalApp(private val context: Context) : OpenExternalApp {
    override fun openEmailApp(email: String, subject: String, body: String) {
        TODO("Not yet implemented")
    }

    override fun openEmailApp() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            this.addCategory(Intent.CATEGORY_APP_EMAIL)
            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent)
    }
}