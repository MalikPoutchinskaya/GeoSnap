package com.kayakstudio.geosnap.tools.android

interface OpenExternalApp {
    fun openEmailApp(email: String, subject: String, body: String)
    fun openEmailApp()
}