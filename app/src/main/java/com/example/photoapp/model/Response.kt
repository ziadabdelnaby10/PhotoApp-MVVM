package com.example.photoapp.model

import com.google.gson.annotations.SerializedName

//@Generated("net.hexar.json2pojo")
data class Response(
    var total: Int,
    var totalHits: Int,
    var hits: ArrayList<Hits>
)