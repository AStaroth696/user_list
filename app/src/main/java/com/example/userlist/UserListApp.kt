package com.example.userlist

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UserListApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(com.example.userlist.di.modules)
            androidContext(this@UserListApp)
        }
    }
}