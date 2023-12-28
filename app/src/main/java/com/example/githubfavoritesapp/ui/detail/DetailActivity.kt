package com.example.githubfavoritesapp.ui.detail

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubfavoritesapp.R
import com.example.githubfavoritesapp.adapter.SectionsPagerAdapter
import com.example.githubfavoritesapp.data.local.entity.UserEntity
import com.example.githubfavoritesapp.databinding.ActivityDetailBinding
import com.example.githubfavoritesapp.ui.favorite.FavoriteViewModel
import com.example.githubfavoritesapp.ui.settings.SettingsViewModel
import com.example.githubfavoritesapp.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel> { ViewModelFactory.getInstance(this) }
    private val settingsViewModel: SettingsViewModel by viewModels<SettingsViewModel> { ViewModelFactory.getInstance(this) }
    private var avatar: String? = null
    private lateinit var tabTextColor: ColorStateList

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsViewModel.getThemeSetting().observe(this) {
            when(it) {
                true -> {
                    tabTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                    binding.tabs.tabTextColors = tabTextColor
                }
                false -> {
                    tabTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
                    binding.tabs.tabTextColors = tabTextColor
                }
            }
        }


        val username = intent.getStringExtra(EXTRA_USER).toString()
        detailViewModel.getDataUser(username)

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.detailData.observe(this) { detailData ->
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(detailData.avatarUrl)
                    .into(profileImg)
                tvName.text = detailData.name
                tvCompany.text = detailData.company ?: "-"
                tvCity.text = detailData.location ?: "-"
                tvUsername.text = detailData.login
                infoUser.sumRepo.text = detailData.publicRepos.toString()
                infoUser.sumFollower.text = detailData.followers.toString()
                infoUser.sumFollowing.text = detailData.following.toString()
            }

            this.avatar = detailData.avatarUrl.toString()
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        sectionsPagerAdapter.username = username
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        favoriteViewModel.getFavoriteUsers().observe(this) { favlist ->
            val isFavorite = favlist.any {
                it.username == username
            }

            setIconButton(isFavorite)

            binding.fabFav.setOnClickListener {
                val entity = username?.let { UserEntity(it, avatar, false) }

                try {
                    if (entity != null) favoriteViewModel.saveUser(entity, favlist.any {
                        it.username == username
                    })
                } catch (e: Exception) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }

                if (isFavorite) {
                    Toast.makeText(this, "Remove $username from Favorite", Toast.LENGTH_SHORT).show()
                    setIconButton(isFavorite)
                } else {
                    Toast.makeText(this, "Add $username to Favorite", Toast.LENGTH_SHORT).show()
                    setIconButton(isFavorite)
                }
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setIconButton(isFavorite: Boolean) {
       binding.fabFav.apply {
           if (isFavorite) {
               setImageDrawable(
                   ContextCompat.getDrawable(
                       this@DetailActivity,
                       R.drawable.ic_favorite_red
                   )
               )
           } else {
               setImageDrawable(
                   ContextCompat.getDrawable(
                       this@DetailActivity,
                       R.drawable.ic_not_favorite
                   )
               )
           }
       }
    }
}