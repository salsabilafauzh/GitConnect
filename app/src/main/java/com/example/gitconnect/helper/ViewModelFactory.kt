package com.example.gitconnect.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitconnect.data.local.repository.FavoriteUserRepository
import com.example.gitconnect.data.local.dataStore.SettingPreferences
import com.example.gitconnect.ui.viewModel.FavoriteViewModel
import com.example.gitconnect.ui.viewModel.SettingViewModel

class ViewModelFactory private constructor(
    private val favoriteUserRepository: FavoriteUserRepository,
    private val settingViewModel: SettingPreferences
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteUserRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(settingViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    Injection.provideDataStore(context.applicationContext)
                )
            }.also { instance = it }
    }
}