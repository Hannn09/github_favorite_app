package com.example.githubfavoritesapp.data

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubfavoritesapp.data.local.entity.UserEntity
import com.example.githubfavoritesapp.data.local.room.UsersDao
import com.example.githubfavoritesapp.data.remote.response.GithubUser
import com.example.githubfavoritesapp.data.remote.response.UserResponse
import com.example.githubfavoritesapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepository private constructor(
    private val apiService: ApiService,
    private val usersDao: UsersDao
){
    private val _listUser = MutableLiveData<List<GithubUser>>()
    val listUser:  LiveData<List<GithubUser>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(username: String) {
        _isLoading.value = true
        val client = apiService.findUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                    Log.d(TAG, "Cek Data : ${response.body()?.items}")
                } else {
                    Log.d(TAG, "On Failure : ${response.message().toString()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "On Failure : ${t.message.toString()}")
            }

        })
    }

    fun getFavoritedUsers(): LiveData<List<UserEntity>> {
        return usersDao.getFavoriteUsers()
    }

    suspend fun setFavoritedUsers(users: UserEntity, favoriteState: Boolean) {
        users.isFavorite = favoriteState
        GlobalScope.launch(Dispatchers.IO) {
            usersDao.insertUsers(users)
            Log.d("User Repository", "Cek Data : $users")
        }
    }

    suspend fun deleteFavoriteUser(users: UserEntity, favoriteState: Boolean) {
        users.isFavorite = favoriteState
        GlobalScope.launch(Dispatchers.IO) {
            usersDao.delete(users)
        }

    }



    companion object {
        private const val TAG = "UserRepository"

        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            apiService: ApiService,
            usersDao: UsersDao
        ): UsersRepository =
            instance ?: synchronized(this) {
                instance ?: UsersRepository(apiService, usersDao)
            }.also { instance = it }
    }
}