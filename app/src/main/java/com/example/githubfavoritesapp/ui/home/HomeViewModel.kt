package com.example.githubfavoritesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubfavoritesapp.data.UsersRepository
import com.example.githubfavoritesapp.data.remote.response.GithubUser
import kotlinx.coroutines.launch

class HomeViewModel(private val usersRepository: UsersRepository): ViewModel() {

    val listUser: LiveData<List<GithubUser>> = usersRepository.listUser
    val isLoading: LiveData<Boolean> = usersRepository.isLoading


    fun getUser(username: String) {
        viewModelScope.launch {
            usersRepository.getUser(username)
        }
    }
}