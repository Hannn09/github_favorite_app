package com.example.githubfavoritesapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubfavoritesapp.data.remote.response.GithubUser
import com.example.githubfavoritesapp.databinding.ItemUserBinding
import com.example.githubfavoritesapp.ui.detail.DetailActivity
import com.example.githubfavoritesapp.ui.detail.DetailActivity.Companion.EXTRA_USER

class UserAdapter(private val listUser: List<GithubUser>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    class ListViewHolder(private val itemBinding: ItemUserBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: GithubUser) {
            itemBinding.apply {
                tvUsername.text = user.login
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(profileUser)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(EXTRA_USER, user.login)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}