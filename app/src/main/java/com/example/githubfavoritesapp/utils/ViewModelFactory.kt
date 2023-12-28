package com.example.githubfavoritesapp.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubfavoritesapp.data.UsersRepository
import com.example.githubfavoritesapp.di.Injection
import com.example.githubfavoritesapp.ui.favorite.FavoriteViewModel
import com.example.githubfavoritesapp.ui.home.HomeViewModel
import com.example.githubfavoritesapp.ui.settings.SettingsViewModel

class ViewModelFactory(private val usersRepository: UsersRepository, private val preferences: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(usersRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(usersRepository) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(preferences) as T
        }

        throw IllegalArgumentException("Unknown View Model class : ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context), SettingPreferences(context.applicationContext as Application))
            }.also { instance = it }
    }
}