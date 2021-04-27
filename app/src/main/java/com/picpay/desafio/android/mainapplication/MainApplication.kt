package com.picpay.desafio.android.mainapplication

import android.app.Application
import com.picpay.desafio.android.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(listOf(mainModule))
        }
    }
}