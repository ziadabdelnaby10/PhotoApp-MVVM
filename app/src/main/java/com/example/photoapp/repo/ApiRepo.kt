package com.example.photoapp.repo

import com.example.photoapp.api.ApiInterface

class ApiRepo(private val service:ApiInterface){
    fun getAllPhotos() = service.getAll()
}