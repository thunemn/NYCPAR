package com.example.nycpar.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET(ApiHelper.parksUrl)
    suspend fun getParks(): Response<List<ParkResponseItem>>
}