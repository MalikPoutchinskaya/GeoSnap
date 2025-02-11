package com.kayakstudio.geosnap.ui.tools.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun ByteArray.convertToBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun ByteArray.convertToImageBitmap(): ImageBitmap {
    return this.convertToBitmap().asImageBitmap()
}