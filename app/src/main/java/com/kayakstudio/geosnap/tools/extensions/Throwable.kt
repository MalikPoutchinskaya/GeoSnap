package com.kayakstudio.geosnap.tools.extensions

fun Throwable.safeMessage() = this.message ?: "Oops an error occurred.."