package com.example.gitconnect.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitconnect.data.remote.response.DetailUserResponse
import com.example.gitconnect.data.remote.response.ItemsItem
import com.example.gitconnect.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _selectedProfile: MutableLiveData<DetailUserResponse> = MutableLiveData()
    val selectedProfile: LiveData<DetailUserResponse> = _selectedProfile
    private val _listFolls: MutableLiveData<List<ItemsItem>?> = MutableLiveData()
    val listFolls: LiveData<List<ItemsItem>?> = _listFolls


    fun getDetailProfile(username: String) {
        _isLoading.value = true
        username.let {
            val client = apiService.getDetailUser(username)
            client.enqueue(
                object : Callback<DetailUserResponse> {
                    override fun onResponse(
                        call: Call<DetailUserResponse>,
                        response: Response<DetailUserResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            responseBody?.let {
                                _selectedProfile.value = it
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<DetailUserResponse>,
                        t: Throwable
                    ) {
                        _isLoading.value = false
                        Log.e(TAG, "OnFailure: ${t.message.toString()}")
                    }
                }
            )
        }
    }


    fun loadFollowers(username: String) {
        _isLoading.value = true
        val client = apiService.getFollowers(username)
        client.enqueue(
            object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _listFolls.value = responseBody
                        } else {
                            _listFolls.value = null
                        }
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "OnFailure: ${t.message.toString()}")
                }
            }
        )
    }

    fun loadFollowing(username: String) {
        _isLoading.value = true
        val client = apiService.getFollowing(username)
        client.enqueue(
            object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _listFolls.value = responseBody
                        } else {
                            _listFolls.value = null
                        }
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "OnFailure: ${t.message.toString()}")
                }

            }
        )
    }
    companion object {
        const val TAG = "DetailViewModel"
    }
}