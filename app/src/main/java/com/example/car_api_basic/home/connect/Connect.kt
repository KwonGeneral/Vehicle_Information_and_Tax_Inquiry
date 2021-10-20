package com.example.car_api_basic.home.connect

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Connect : ConnectInterface {
    override fun connect(): Retrofit {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request:Request = chain.request().newBuilder().addHeader("Authorization", "78e2ec1adb54495eafae8553fe4399ff9a34f701").build()
            chain.proceed(request)
        }

        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://datahub-dev.scraping.co.kr")
            .client(httpClient.build())
            .build()

        return retrofit
    }
}

