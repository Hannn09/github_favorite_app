package com.example.githubfavoritesapp.ui.detail.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfavoritesapp.R
import com.example.githubfavoritesapp.adapter.FollowAdapter
import com.example.githubfavoritesapp.data.remote.response.FollowResponseItem
import com.example.githubfavoritesapp.databinding.FragmentFollowBinding
import com.example.githubfavoritesapp.ui.detail.DetailViewModel


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private var position: Int = 0
    private var username: String? = null
    private val detailViewModel by viewModels<DetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION, 0)
            username = it.getString(ARG_USERNAME)
        }

        showFollow()
        if (position == 1) {
            username?.let { detailViewModel.getListFollower(it) }
            detailViewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
                if (!listFollower.isNullOrEmpty()) {
                    binding.rvFollow.adapter = FollowAdapter(listFollower)
                } else {
                    Toast.makeText(requireActivity(), "Followers Kosong", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            username?.let { detailViewModel.getListFollowing(it) }
            detailViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                if (!listFollowing.isNullOrEmpty()) {
                    binding.rvFollow.adapter = FollowAdapter(listFollowing)
                } else {
                    Toast.makeText(requireActivity(), "Followers Kosong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun showFollow() {
        binding.rvFollow.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}