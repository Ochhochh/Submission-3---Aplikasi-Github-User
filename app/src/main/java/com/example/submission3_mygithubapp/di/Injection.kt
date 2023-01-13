package com.example.submission3_mygithubapp.di

import android.content.Context
import com.example.submission3_mygithubapp.data.UserRepository
import com.example.submission3_mygithubapp.data.lokal.room.UserDatabase
import com.example.submission3_mygithubapp.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}