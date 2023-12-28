package com.example.githubfavoritesapp.data.remote.retrofit

import com.example.githubfavoritesapp.data.remote.response.DetailUserResponse
import com.example.githubfavoritesapp.data.remote.response.FollowResponseItem
import com.example.githubfavoritesapp.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun findUser(
        @Query("q") query: String
    ) : Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String?
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String?
    ) : Call<List<FollowResponseItem>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String?
    ) : Call<List<FollowResponseItem>>
}