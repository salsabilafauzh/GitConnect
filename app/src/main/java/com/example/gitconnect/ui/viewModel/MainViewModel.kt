package com.example.gitconnect.ui.viewModel


import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitconnect.data.remote.response.ItemsItem
import com.example.gitconnect.data.remote.response.SearchResponse
import com.example.gitconnect.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    private val _profileList: MutableLiveData<List<ItemsItem?>> = MutableLiveData()
    val profileList: LiveData<List<ItemsItem?>> = _profileList
    private val _username = MutableLiveData<String>()
    val uname: LiveData<String> = _username
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadProfile(username: String) {
        _isLoading.value = true
        try {
            val client = apiService.searchUser(username)
            client.enqueue(
                object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.items.let {
                                _profileList.value = it
                            }
                        }
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.e(TAG, "OnFailure: ${t.message.toString()}")
                    }
                }
            )

        } catch (e: IOException) {
            Log.e(TAG, "OnFailure: ${e.message.toString()}")
        }

    }

    fun setUsername(username: String) {
        _username.value = username
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
