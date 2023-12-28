package com.example.githubfavoritesapp.utils

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class  SettingPreferences(application: Application) {
    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="settings")
    private val dataStoreTheme = application.dataStore

    fun getThemeSetting(): Flow<Boolean> {
        return dataStoreTheme.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStoreTheme.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }


}