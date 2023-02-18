package com.example.nycpar.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {

    const val baseUrl = "https://data.cityofnewyork.us"
    const val parksUrl = "/resource/vjbm-hsyr.json"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}