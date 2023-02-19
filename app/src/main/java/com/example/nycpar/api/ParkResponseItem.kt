package com.example.nycpar.api

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ParkResponseItem() : RealmObject() {
    @PrimaryKey
    @SerializedName("park_name")
    var parkName: String? = null
    @SerializedName("width_ft")
    var widthFT: String? = null
    @SerializedName("class")
    var parkClass: String? = null
    @SerializedName("surface")
    var surface: String? = null
    @SerializedName("gen_topog")
    var topog: String? = null
    @SerializedName("difficulty")
    var difficulty: String? = null
    @SerializedName("date_collected")
    var dateCollected: String? = null
    @SerializedName("trail_name")
    var trailName: String? = null
    @SerializedName("parkid")
    var parkId: String? = null
    @SerializedName("trailmarkersinstalled")
    var trailMarkersInstalled: String? = null
}