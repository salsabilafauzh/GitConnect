package com.example.gitconnect.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import retrofit2.http.GET

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUserEntity)

    @Delete
    fun delete(favoriteUser: FavoriteUserEntity)

    @Query("SELECT * FROM favorite ORDER BY username ASC")
    fun getAllFavorite(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>

}