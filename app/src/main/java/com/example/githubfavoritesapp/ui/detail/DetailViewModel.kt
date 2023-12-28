package com.example.githubfavoritesapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubfavoritesapp.data.remote.response.DetailUserResponse
import com.example.githubfavoritesapp.data.remote.response.FollowResponse
import com.example.githubfavoritesapp.data.remote.response.FollowResponseItem
import com.example.githubfavoritesapp.data.remote.retrofit.ApiConfig
import com.example.githubfavoritesapp.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailData = MutableLiveData<DetailUserResponse>()
    val detailData: LiveData<DetailUserResponse> = _detailData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading :  LiveData<Boolean> = _isLoading

    private val _listFollower = MutableLiveData<List<FollowResponseItem>>()
    val listFollower: LiveData<List<FollowResponseItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<FollowResponseItem>>()
    val listFollowing: LiveData<List<FollowResponseItem>> = _listFollowing

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDataUser(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailData.value = response.body()
                } else {
                    Log.d(TAG, "On Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "Error Fetching Data : ${t.message}")
            }
        })
    }

    fun getListFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUser(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                } else {
                    Log.d(TAG, "On Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.d(TAG, "Error Fetching Data : ${t.message}")
            }
        })
    }

    fun getListFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingUser(username)
        client.enqueue(object : Callback<List<FollowResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.d(TAG, "On Failure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.d(TAG, "Error Fetching Data : ${t.message}")
            }
        })
    }

}