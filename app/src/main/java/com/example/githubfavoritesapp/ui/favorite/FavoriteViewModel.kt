package com.example.githubfavoritesapp.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubfavoritesapp.data.UsersRepository
import com.example.githubfavoritesapp.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    fun getFavoriteUsers() = usersRepository.getFavoritedUsers()

    fun saveUser(userEntity: UserEntity, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                usersRepository.deleteFavoriteUser(userEntity, false)
                Log.d("User Repository", "Cek : $userEntity")
            } else {
                usersRepository.setFavoritedUsers(userEntity, true)
            }
        }
    }
}