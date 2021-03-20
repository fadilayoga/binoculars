package com.udacoding.getcrud.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkModule {

    fun interceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://lifter.000webhostapp.com/tanaman/")
            .client(interceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitPariwisata(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.5/pariwisata/")
            .client(interceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun servicePariwisata(): ApiService = getRetrofitPariwisata().create(ApiService::class.java)

    fun service(): ApiService = getRetrofit().create(ApiService::class.java)
}