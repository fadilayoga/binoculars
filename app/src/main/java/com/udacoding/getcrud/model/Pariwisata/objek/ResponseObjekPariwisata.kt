package com.udacoding.getcrud.model.Pariwisata.objek

import com.google.gson.annotations.SerializedName

data class ResponseObjekPariwisata (

    @field:SerializedName("data")
    val data: List<DataItemObjekPariwisata?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("isSuccess")
    val isSuccess: Boolean? = null

)