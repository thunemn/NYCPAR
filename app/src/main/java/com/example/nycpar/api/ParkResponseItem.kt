package com.example.nycpar.api

import com.google.gson.annotations.SerializedName

data class ParkResponseItem(
    @SerializedName("park_name")
    val parkName: String?,
    @SerializedName("width_ft")
    val widthFT: String?,
    @SerializedName("class")
    val parkClass: String?,
    @SerializedName("surface")
    val surface: String?,
    @SerializedName("difficulty")
    val difficulty: String?,
    @SerializedName("date_collected")
    val dateCollected: String?,
    @SerializedName("trail_name")
    val trailName: String?,
    @SerializedName("parkid")
    val parkId: String?,
    @SerializedName("trailmarkersinstalled")
    val trailMarkersInstalled: String?,
)
