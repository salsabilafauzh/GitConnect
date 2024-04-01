package com.example.gitconnect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.gitconnect.databinding.ActivitySettingBinding
import com.example.gitconnect.helper.ViewModelFactory
import com.example.gitconnect.ui.viewModel.SettingViewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel.getThemeSettings()
            .observe(this@SettingActivity) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.switchTheme.isChecked = false
                }
            }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}