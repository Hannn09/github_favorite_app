package com.example.githubfavoritesapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.githubfavoritesapp.MainActivity
import com.example.githubfavoritesapp.R
import com.example.githubfavoritesapp.ui.settings.SettingsViewModel
import com.example.githubfavoritesapp.utils.SettingPreferences
import com.example.githubfavoritesapp.utils.ViewModelFactory
import com.google.gson.Gson

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME: Long = 3000
    private val settingsViewModel: SettingsViewModel by viewModels<SettingsViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        settingsViewModel.getThemeSetting().observe(this) {
            when(it) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        @Suppress("DEPRECATION")
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME)
    }
}