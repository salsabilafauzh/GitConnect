package com.example.gitconnect.ui.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gitconnect.data.local.database.FavoriteUserEntity
import com.example.gitconnect.data.local.repository.FavoriteUserRepository

class FavoriteViewModel(private val userRepository: FavoriteUserRepository) : ViewModel() {
    fun getAllFavorite(): LiveData<List<FavoriteUserEntity>> {
        return userRepository.getFavoriteUser()
    }

    fun checkIsFavorite(username: String): LiveData<FavoriteUserEntity> {
        return userRepository.checkFavorite(username)
    }

    fun addFavorite(userEntity: FavoriteUserEntity) {
        userRepository.setFavoriteUser(userEntity)
    }

    fun deleteFavorite(userEntity: FavoriteUserEntity) {
        userRepository.deleteFavoriteUser(userEntity)
    }

}