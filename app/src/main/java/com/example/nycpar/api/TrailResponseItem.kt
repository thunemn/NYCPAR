package com.example.nycpar.api

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TrailResponseItem : RealmObject() {
    @SerializedName("park_name")
    var parkName: String? = null
    @SerializedName("width_ft")
    var widthFT: String? = null
    @SerializedName("class")
    var trailClass: String? = null
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

    @PrimaryKey
    var primaryKey: String? = null
        set(unused) {
            field = "$trailName/$parkName"
        }
    var isFavorite = false

//    override fun equals(other: Any?): Boolean {
//        return false
//    }
}