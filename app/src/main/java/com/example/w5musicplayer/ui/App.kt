package com.example.w5musicplayer.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.w5musicplayer.ui.repo.GetDataFromInternalStorage
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin

class App : Application() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@App)
            modules(listOf(module))

        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    var module = org.koin.dsl.module {

        viewModel {
            SharedViewModel(get(), get())
        }

        factory {
            App()
        }

        single {
            GetDataFromInternalStorage(get())
        }

    }
}