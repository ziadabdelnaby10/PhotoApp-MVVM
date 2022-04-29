package com.example.photoapp.api

import android.content.Context
import com.example.photoapp.util.Constants
import com.example.photoapp.util.UtilFunctions
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{

    var retrofitInstance : ApiInterface? = null

    fun getInstance(context:Context) : ApiInterface?{
        if(retrofitInstance == null){
            retrofitInstance = Retrofit.Builder()
                .baseUrl(Constants.URL)
                .client(createOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
        return retrofitInstance
    }

    private fun createOkHttpClient(context:Context) : OkHttpClient{
        val myCache = Cache(context.cacheDir, Constants.CACHE_SIZE)
        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (UtilFunctions.hasNetwork(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 20).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
            .build()
        return okHttpClient
    }



}