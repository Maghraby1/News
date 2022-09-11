package com.maghraby.news

import android.app.Application
import com.maghraby.news.di.module.appModule
import com.maghraby.news.di.module.repoModule
import com.maghraby.news.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}