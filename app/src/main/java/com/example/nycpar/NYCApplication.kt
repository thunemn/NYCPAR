package com.example.nycpar

import androidx.multidex.MultiDexApplication
import io.realm.Realm
import io.realm.RealmConfiguration

class NYCApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build()
        )
    }
}