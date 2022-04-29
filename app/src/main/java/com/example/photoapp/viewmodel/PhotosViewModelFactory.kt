package com.example.photoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photoapp.repo.ApiRepo
import com.example.photoapp.repo.DatabaseRepo

class PhotosViewModelFactory(private val apiRepo: ApiRepo, private val databaseRepo : DatabaseRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PhotosActivityViewModel::class.java)) {
            PhotosActivityViewModel(this.apiRepo , this.databaseRepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}