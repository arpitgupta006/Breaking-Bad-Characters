package com.arpit.breakingbadcharacters

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface APIRequest {

    @GET("character/random")

    suspend fun getInfo() : Response<List<ResponseBBItem>>
}