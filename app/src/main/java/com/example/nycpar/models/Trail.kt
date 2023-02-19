package com.example.nycpar.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Trail() : RealmObject() {
    @PrimaryKey
    var trailName: String? = null
    var parkName: String? = null
    var widthFT: String? = null
    var parkClass: String? = null
    var surface: String? = null
    var topog: String? = null
    var difficulty: String? = null
    var dateCollected: String? = null
    var parkId: String? = null
    var trailMarkersInstalled: String? = null
}