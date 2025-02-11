package com.kayakstudio.geosnap.data.document

import com.kayakstudio.geosnap.data.apiutils.map

class DocumentRepository(
    private val documentApi: DocumentApi,
) {

    //
    // GET
    // -----------------------------------------------------


    //
    // Insert
    // -----------------------------------------------------

    suspend fun postProfilePicture(
        userId: String,
        img: ByteArray,
    ): Result<String> {
        return documentApi.postProfilePicture(
            userId = userId, img = img
        ).map { it }
    }

    suspend fun postGeoGuessPicture(
        userId: String,
        img: ByteArray,
    ): Result<String> {
        return documentApi.postGeoGuesserPicture(
            userId = userId, img = img
        ).map { it }
    }


    //
    // Delete
    // -----------------------------------------------------

}
