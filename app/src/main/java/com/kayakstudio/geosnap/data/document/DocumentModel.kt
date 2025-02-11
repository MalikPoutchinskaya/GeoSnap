package com.kayakstudio.geosnap.data.document

class DocumentModel(
    val id: Int,
    val name: String,
    val contentType: String? = null,
    val img: ByteArray? = null,
)