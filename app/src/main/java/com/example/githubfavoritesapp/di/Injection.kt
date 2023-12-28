package com.example.githubfavoritesapp.di

import android.content.Context
import com.example.githubfavoritesapp.data.UsersRepository
import com.example.githubfavoritesapp.data.local.room.UsersDatabase
import com.example.githubfavoritesapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UsersRepository {
        val apiService = ApiConfig.getApiService()
        val database = UsersDatabase.getInstance(context)
        val dao = database.usersDao()
        return UsersRepository.getInstance(apiService, dao)
    }
}