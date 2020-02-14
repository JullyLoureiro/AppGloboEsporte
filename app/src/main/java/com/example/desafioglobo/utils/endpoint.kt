package com.example.desafioglobo.utils

import com.example.desafioglobo.model.Artigos
import retrofit2.Call
import retrofit2.http.GET

interface endpoint {
    @GET("artigos")
    fun getArtigos() : Call<List<Artigos>>
}