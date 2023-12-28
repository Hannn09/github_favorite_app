package com.example.githubfavoritesapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubfavoritesapp.data.remote.response.FollowResponseItem
import com.example.githubfavoritesapp.data.remote.response.GithubUser
import com.example.githubfavoritesapp.databinding.ItemUserBinding
import com.example.githubfavoritesapp.ui.detail.DetailActivity

class FollowAdapter(private val listFollow: List<FollowResponseItem>) : RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {
    class ListViewHolder(private val itemBinding: ItemUserBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(follow: FollowResponseItem) {
            itemBinding.apply {
                tvUsername.text = follow.login
                Glide.with(itemView.context)
                    .load(follow.avatarUrl)
                    .into(profileUser)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = listFollow.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollow[position])
    }
}