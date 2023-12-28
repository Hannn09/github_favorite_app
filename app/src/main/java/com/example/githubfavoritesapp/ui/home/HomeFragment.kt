package com.example.githubfavoritesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfavoritesapp.adapter.UserAdapter
import com.example.githubfavoritesapp.databinding.FragmentHomeBinding
import com.example.githubfavoritesapp.utils.ViewModelFactory


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by activityViewModels<HomeViewModel> { ViewModelFactory.getInstance(requireActivity()) }
    private lateinit var userQuery: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                userQuery = query.toString()
                binding.searchView.clearFocus()
                val getData = homeViewModel.getUser(userQuery)
                if (userQuery.isEmpty() || getData.equals(null)) {
                    binding.rvUser.adapter = UserAdapter(emptyList())
                    showContent(true)
                } else {
                    showContent(false)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userQuery = newText.toString()
                if (userQuery.isEmpty()) {
                    binding.rvUser.adapter = UserAdapter(emptyList())
                    showContent(true)
                } else {
                    binding.rvUser.adapter = UserAdapter(emptyList())
                    showContent(false)
                }
                return true
            }

        })

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        showUser()
        homeViewModel.listUser.observe(viewLifecycleOwner) { listUser ->
            if (!listUser.isNullOrEmpty()) {
                binding.rvUser.adapter = UserAdapter(listUser)
            } else {
                Toast.makeText(requireActivity(), "No Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rvUser.adapter = UserAdapter(emptyList())
    }

    private fun showUser() {
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showContent(isShow: Boolean) {
        binding.imgSearch.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
        binding.tvSearching.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

}