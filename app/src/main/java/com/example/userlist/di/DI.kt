package com.example.userlist.di

import com.example.userlist.data.api.UserApiService
import com.example.userlist.manager.MainDataManager
import com.example.userlist.manager.MainDataManagerImpl
import com.example.userlist.util.BASE_URL
import com.example.userlist.viewmodel.MainViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val dataModule = module {
    factory {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(UserApiService::class.java)
    }
    factory { MainDataManagerImpl(get()) as MainDataManager }
}

private val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val modules = mutableListOf(dataModule, viewModelModule)