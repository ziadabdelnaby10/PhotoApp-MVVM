package com.example.photoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "hits")
data class HitEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    var webformatURL: String,
    var largeImageURL: String,
    var views: Int,
    var downloads: Int,
    var likes: Int,
    var comments: Int,
    var userId: Int,
    var user: String,
    var userImageURL: String
)
