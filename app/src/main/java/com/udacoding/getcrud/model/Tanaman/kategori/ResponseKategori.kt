package com.udacoding.getcrud.model.Tanaman.kategori

import com.google.gson.annotations.SerializedName

data class ResponseKategori(

    @field:SerializedName("data")
    val data: List<DataItemKategori?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean? = null
)