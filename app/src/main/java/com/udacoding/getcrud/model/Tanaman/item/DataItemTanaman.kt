package com.udacoding.getcrud.model.Tanaman.item

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemTanaman(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null

): Parcelable