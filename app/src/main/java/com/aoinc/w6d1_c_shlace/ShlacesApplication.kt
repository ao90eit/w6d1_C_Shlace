package com.aoinc.w6d1_c_shlace

import android.app.Application
import com.google.firebase.FirebaseApp

// first class that is attaced to the application
class ShlacesApplication : Application() {

    // first onCreate() called EVER.
    override fun onCreate() {
        super.onCreate()
//        FirebaseApp.initializeApp(this)
    }
}