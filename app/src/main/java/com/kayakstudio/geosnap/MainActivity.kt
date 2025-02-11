package com.kayakstudio.geosnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.kayakstudio.geosnap.ui.features.App
import io.github.vinceglb.filekit.core.FileKit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        FileKit.init(this)

        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true // dynamic color true for android
            )
        }
    }
}