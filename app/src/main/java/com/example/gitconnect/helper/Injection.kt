package com.example.gitconnect.helper

import android.content.Context
import com.example.gitconnect.data.local.database.FavoriteUserDatabase
import com.example.gitconnect.data.local.repository.FavoriteUserRepository
import com.example.gitconnect.data.local.dataStore.SettingPreferences
import com.example.gitconnect.data.local.dataStore.dataStore

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val database = FavoriteUserDatabase.getDatabase(context)
        val favoriteUserDao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return FavoriteUserRepository.getInstance(favoriteUserDao, appExecutors)
    }

    fun provideDataStore(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.dataStore)
    }
}