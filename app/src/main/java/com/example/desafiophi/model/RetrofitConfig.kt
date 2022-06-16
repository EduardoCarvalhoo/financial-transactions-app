package com.example.desafiophi.model

import com.example.desafiophi.ExtractConfig
import com.example.desafiophi.api.ExtractService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {

    companion object{
        fun getRetrofit(): ExtractService {
            val retrofit = Retrofit.Builder().baseUrl(ExtractConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ExtractService::class.java)
            return retrofit


        }
    }
}