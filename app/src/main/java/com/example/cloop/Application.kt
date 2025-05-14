package com.example.cloop

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class CLOOP : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}