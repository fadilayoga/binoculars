package com.udacoding.getcrud.network

import com.udacoding.getcrud.model.Pariwisata.kategori.ResponseKategoriPariwisata
import com.udacoding.getcrud.model.Pariwisata.objek.ResponseObjekPariwisata
import com.udacoding.getcrud.model.Tanaman.action.ResponseAction
import com.udacoding.getcrud.model.Tanaman.item.ResponseTanaman
import com.udacoding.getcrud.model.Tanaman.kategori.ResponseKategori
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //getKategori
    @GET("kategori/getData.php")
    fun getDataKategori(): Call<ResponseKategori>

    //hapuskategori
    @FormUrlEncoded
    @POST("kategori/delete.php")
    fun deletekategori(
        @Field("kategori") kategori: String
    ): Call<ResponseAction>

    //updatekategori
    @FormUrlEncoded
    @POST("kategori/update.php")
    fun updatekategori(
        @Field("kategori") kategori: String,
        @Field("kategori_old") kategori_old: String
    ): Call<ResponseAction>

    //insertKategori
    @FormUrlEncoded
    @POST("kategori/insert.php")
    fun insertkategori(
        @Field("kategori") kategori: String
    ): Call<ResponseAction>

//..................................................................................................

    //getDataTanaman
    @FormUrlEncoded
    @POST("item/getData.php")
    fun getDataTanaman(
        @Field("kategori") kategori: String
    ): Call<ResponseTanaman>

    //hapuskategori
    @FormUrlEncoded
    @POST("item/delete.php")
    fun deleteItem(
        @Field("id") id: String
    ): Call<ResponseAction>

    //updateTanaman
    @FormUrlEncoded
    @POST("item/update.php")
    fun updateTanaman(
        @Field("id") id: String,
        @Field("nama") nama: String,
        @Field("deskripsi") deskripsi: String,
        @Field("image") image: String,
        @Field("harga") harga: String,
        @Field("kategori") kategori: String,
    ): Call<ResponseAction>

    //insertTanaman
    @FormUrlEncoded
    @POST("item/insert.php")
    fun insertTanaman(
        @Field("id") id: String,
        @Field("nama") nama: String,
        @Field("deskripsi") deskripsi: String,
        @Field("image") image: String,
        @Field("harga") harga: String,
        @Field("kategori") kategori: String,
    ): Call<ResponseAction>

////////////////////////////////////////////////////////////////////////////////////////////////////

    //getPariwisataKategori
    @GET("kategori/getData.php")
    fun getDataKategoriPariwisata(): Call<ResponseKategoriPariwisata>

    //getPariwisataObjek
    @FormUrlEncoded
    @POST("objek/getData.php")
    fun getDataObjekPariwisata(
        @Field("id") id: String
    ): Call<ResponseObjekPariwisata>
}