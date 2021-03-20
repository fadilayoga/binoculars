package com.udacoding.getcrud.model.Pariwisata.kategori

import com.google.gson.annotations.SerializedName

data class ResponseKategoriPariwisata(

	@field:SerializedName("data")
	val data: List<DataItemKategoriPariwisata?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null
)