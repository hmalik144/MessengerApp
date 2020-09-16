package com.example.h_mal.messengerapp.application

import android.app.Application
import com.example.h_mal.messengerapp.data.network.MessengerApi
import com.example.h_mal.messengerapp.data.network.NetworkConnectionInterceptor
import com.example.h_mal.messengerapp.data.network.okHttpClient
import com.example.h_mal.messengerapp.data.repository.RepositoryImpl
import com.example.h_mal.messengerapp.data.room.AppRoomDatabase
import com.example.h_mal.messengerapp.ui.main.MainViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppClass: Application(), KodeinAware{

    // Kodein aware to initialise the classes used for DI
    // Simple DI module creation where @instance() automatically becomes required class (eg. context or singleton)
    @ExperimentalCoroutinesApi
    override val kodein = Kodein.lazy {
        import(androidXModule(this@AppClass))

        bind() from singleton {
            NetworkConnectionInterceptor(
                instance()
            )
        }
        bind() from singleton {
            okHttpClient(
                instance()
            )
        }
        bind() from singleton {
            MessengerApi(
                instance()
            )
        }
        bind() from singleton { AppRoomDatabase(instance()) }
        bind() from singleton { RepositoryImpl(instance(), instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }
}