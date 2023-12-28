package com.example.githubfavoritesapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<GithubUser>
)

data class GithubUser(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: Any,

	@field:SerializedName("public_repos")
	val repository: Int,
)

