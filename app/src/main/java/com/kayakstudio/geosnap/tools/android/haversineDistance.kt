package com.kayakstudio.geosnap.tools.android

import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

fun haversineDistance(start: LatLng, end: LatLng): Double {
    val R = 6371e3 // Rayon de la Terre en mètres

    val lat1 = Math.toRadians(start.latitude)
    val lat2 = Math.toRadians(end.latitude)
    val dLat = Math.toRadians(end.latitude - start.latitude)
    val dLon = Math.toRadians(end.longitude - start.longitude)

    val a = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c // Distance en mètres
}