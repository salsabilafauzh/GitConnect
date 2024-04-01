package com.example.gitconnect.data.remote.retrofit

import com.example.gitconnect.data.remote.response.DetailUserResponse
import com.example.gitconnect.data.remote.response.ItemsItem
import com.example.gitconnect.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("/users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("/users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}