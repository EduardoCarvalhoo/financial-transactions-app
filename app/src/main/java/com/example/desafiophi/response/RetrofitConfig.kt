package com.example.desafiophi.response

import com.example.desafiophi.api.ExtractService
import com.example.desafiophi.utils.ExtractConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitConfig {

    companion object{
        fun getRetrofit(): ExtractService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(ExtractConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ExtractService::class.java)
            return retrofit
        }
    }
}