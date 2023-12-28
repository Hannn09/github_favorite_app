package com.example.githubfavoritesapp.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.githubfavoritesapp.R
import com.example.githubfavoritesapp.databinding.FragmentSettingsBinding
import com.example.githubfavoritesapp.utils.ViewModelFactory


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val settingViewModel by activityViewModels<SettingsViewModel> { ViewModelFactory.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        darkMode()
    }

    private fun darkMode() {
        settingViewModel.getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            binding.ivDark.setOnClickListener {
                settingViewModel.saveThemeSetting(!isDarkModeActive)
            }

            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.ivDark.setImageResource(R.drawable.ic_moon)
                binding.tvDark.text = resources.getText(R.string.on_light_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.ivDark.setImageResource(R.drawable.ic_sun)
                binding.tvDark.text = resources.getText(R.string.on_dark_mode)
            }


        }
    }

}