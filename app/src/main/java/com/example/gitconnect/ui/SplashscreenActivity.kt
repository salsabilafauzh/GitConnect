package com.example.gitconnect.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.gitconnect.R
import com.example.gitconnect.databinding.ActivitySplashscreenBinding
import com.example.gitconnect.helper.ViewModelFactory
import com.example.gitconnect.ui.viewModel.SettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    private val settingViewModel: SettingViewModel by viewModels {
        ViewModelFactory.getInstance(
            application
        )
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        lifecycleScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                val intent = Intent(this@SplashscreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        settingViewModel.getThemeSettings()
            .observe(this@SplashscreenActivity) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.icon.setImageResource(R.drawable.icon_dark_mode)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.icon.setImageResource(R.drawable.icon_light_mode)
                }
            }
    }
}