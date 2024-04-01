package com.example.gitconnect.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "username")
    var username: String = "",
    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null
)
