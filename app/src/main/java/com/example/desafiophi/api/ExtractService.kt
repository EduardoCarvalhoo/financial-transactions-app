package com.example.desafiophi.api

import com.example.desafiophi.model.ExtractList
import com.example.desafiophi.model.MyBalance
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ExtractService {

    @GET("myBalance")
    fun getMyBalance(@Header("token")token: String): Call<MyBalance>

    @GET("myStatement/10/0")
    fun getMyStatement(@Header("token")token: String ): Call<ExtractList>

    @GET("myStatement/detail/{id}")
    fun getMyStatementDetail(@Header("token") token: String, @Header("id") id: String?): Call<ExtractList>


}