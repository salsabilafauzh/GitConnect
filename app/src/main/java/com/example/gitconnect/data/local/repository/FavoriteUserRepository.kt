package com.example.gitconnect.data.local.repository

import androidx.lifecycle.LiveData
import com.example.gitconnect.data.local.database.FavoriteUserDao
import com.example.gitconnect.data.local.database.FavoriteUserEntity
import com.example.gitconnect.helper.AppExecutors

class FavoriteUserRepository private constructor(
    private val mFavoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
) {
    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>> {
        return mFavoriteUserDao.getAllFavorite()
    }

    fun checkFavorite(username:String): LiveData<FavoriteUserEntity> {
        return mFavoriteUserDao.getFavoriteUserByUsername(username)
    }

    fun setFavoriteUser(userEntity: FavoriteUserEntity) {
        appExecutors.diskIO.execute {
            mFavoriteUserDao.insert(userEntity)
        }
    }

    fun deleteFavoriteUser(userEntity: FavoriteUserEntity) {
        appExecutors.diskIO.execute {
            mFavoriteUserDao.delete(userEntity)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            favoriteUserDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): FavoriteUserRepository = instance ?: synchronized(this) {
            instance ?: FavoriteUserRepository( favoriteUserDao, appExecutors)
        }.also { instance = it }
    }
}