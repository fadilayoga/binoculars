package com.udacoding.getcrud.model.Pariwisata.objek

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemObjekPariwisata(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("Nama")
    val Nama: String? = null,

    @field:SerializedName("Lokasi")
    val Lokasi: String? = null,

    @field:SerializedName("Deskripsi")
    val Deskripsi: String? = null,

    @field:SerializedName("Kategori")
    val Kategori: String? = null,

    @field:SerializedName("Image")
    val Image: String? = null,

): Parcelable