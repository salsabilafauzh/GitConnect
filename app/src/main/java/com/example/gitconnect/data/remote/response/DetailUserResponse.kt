package com.example.gitconnect.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserResponse(
    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    ) : Parcelable
