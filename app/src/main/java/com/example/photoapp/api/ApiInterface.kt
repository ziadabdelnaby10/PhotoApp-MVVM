package com.example.photoapp.api

import com.example.photoapp.model.Response
import com.example.photoapp.util.Constants
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET(Constants.KEY)
    fun getAll(): Call<Response>
}