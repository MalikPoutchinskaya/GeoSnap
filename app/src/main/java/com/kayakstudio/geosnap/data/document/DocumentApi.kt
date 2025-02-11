package com.kayakstudio.geosnap.data.document

import com.kayakstudio.geosnap.data.apiutils.ApiResponse
import com.kayakstudio.geosnap.data.apiutils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

interface DocumentApi {
    suspend fun postProfilePicture(
        userId: String,
        img: ByteArray,
    ): ApiResponse<String>

    suspend fun postGeoGuesserPicture(
        userId: String,
        img: ByteArray,
    ): ApiResponse<String>
}

class KtorDocumentApi(
    private val client: HttpClient,
) : DocumentApi {
    override suspend fun postProfilePicture(
        userId: String,
        img: ByteArray,
    ): ApiResponse<String> {
        return safeRequest {
            client.submitFormWithBinaryData(
                url = "/users/$userId/profile/upload",
                formData = formData {
                    append(
                        key = "file",
                        value = img,
                        headers = Headers.build {
                            append(
                                name = HttpHeaders.ContentDisposition,
                                value = "filename=profile_picture"
                            )
                            append(
                                name = HttpHeaders.ContentType,
                                value = "image/jpg"
                            )
                        }
                    )
                }
            )
        }
    }

    override suspend fun postGeoGuesserPicture(
        userId: String,
        img: ByteArray,
    ): ApiResponse<String> {
        return safeRequest {
            client.submitFormWithBinaryData(
                url = "/users/$userId/geoguesser/upload",
                formData = formData {
                    append(
                        key = "file",
                        value = img,
                        headers = Headers.build {
                            append(
                                name = HttpHeaders.ContentDisposition,
                                value = "filename=geo_guesser"
                            )
                            append(
                                name = HttpHeaders.ContentType,
                                value = "image/jpg"
                            )
                        }
                    )
                }
            )
        }
    }
}