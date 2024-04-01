package com.example.gitconnect.data.remote.retrofit

import com.example.gitconnect.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {
        private val authInterceptor = Interceptor { chain ->
            val key = BuildConfig.API_TOKEN
            val req = chain.request()
            val requestHeader = req.newBuilder()
                .addHeader("Authorization", key).build()
            chain.proceed(requestHeader)
        }

        fun getApiService(): ApiService {
            val baseUrl = BuildConfig.BASE_URL
            val client = OkHttpClient.Builder().addInterceptor(authInterceptor).build()
            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(ApiService::class.java)
        }
    }
}