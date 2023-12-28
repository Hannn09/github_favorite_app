package com.example.githubfavoritesapp.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfavoritesapp.R
import com.example.githubfavoritesapp.adapter.FavoriteAdapter
import com.example.githubfavoritesapp.databinding.FragmentFavoriteBinding
import com.example.githubfavoritesapp.utils.ViewModelFactory


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteViewModel by activityViewModels<FavoriteViewModel> { ViewModelFactory.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdapter()
        showRecylerList()
        favoriteViewModel.getFavoriteUsers().observe(viewLifecycleOwner) { favlist ->
           favoriteAdapter.updateUser(favlist)
            Log.d("FavoriteFragment", "Cek Favorite : $favlist")
        }
    }

    private fun showRecylerList() {
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }


}