package com.example.desafiophi.api

import com.example.desafiophi.response.ExtractItemDetailResponse
import com.example.desafiophi.response.ExtractListResponse
import com.example.desafiophi.response.MyBalanceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface ExtractService {

    @GET("myBalance")
    fun getMyBalance(@Header("token")token: String): Call<MyBalanceResponse>

    @GET("myStatement/10/0")
    fun getMyStatement(@Header("token")token: String ): Call<ExtractListResponse>

    @GET("myStatement/detail/{id}")
    fun getMyStatementDetail(@Header("token") token: String, @Path("id") id: String?): Call<ExtractItemDetailResponse>



}