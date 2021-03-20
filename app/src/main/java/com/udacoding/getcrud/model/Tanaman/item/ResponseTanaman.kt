package com.udacoding.getcrud.model.Tanaman.item

import com.google.gson.annotations.SerializedName

data class ResponseTanaman(

	@field:SerializedName("data")
	val data: List<DataItemTanaman?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)