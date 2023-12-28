package com.example.githubfavoritesapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class FollowResponse(
    val followResponse: List<FollowResponseItem>
)

data class FollowResponseItem(
    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,


    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    )
