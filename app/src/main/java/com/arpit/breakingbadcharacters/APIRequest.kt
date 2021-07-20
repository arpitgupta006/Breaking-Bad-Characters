package com.arpit.breakingbadcharacters

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface APIRequest {

    @GET("character/random")

    suspend fun getInfo() : Response<List<ResponseBBItem>>
}

//Picasso.get().load(data.img).into(ivImage)
//tvName.text = data.name
//tvOccupation.text = data.occupation.toString()
//tvActor.text = data.portrayed
//tvAppearance.text = data.appearance.toString()
//tvStatus.text = data.status