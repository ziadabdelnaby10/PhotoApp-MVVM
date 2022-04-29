package com.example.photoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoapp.model.HitEntity
import com.example.photoapp.model.Hits
import com.example.photoapp.model.Response
import com.example.photoapp.repo.ApiRepo
import com.example.photoapp.repo.DatabaseRepo
import com.example.photoapp.util.UtilFunctions.Companion.notifyObserver
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback


class PhotosActivityViewModel(
    private val apiRepo: ApiRepo,
    private val databaseRepo: DatabaseRepo,
) : ViewModel() {

    private val _photosList = MutableLiveData<Response>()
    val photosList: LiveData<Response>
        get() = _photosList

    private val _savedPhotosList = MutableLiveData<List<HitEntity>>()
    val savedPhotosList: LiveData<List<HitEntity>>
        get() = _savedPhotosList

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: MutableLiveData<String>
        get() = _errorMsg

    init {
        getAllPhotosFromDatabase()
        getAllPhotosFromAPI()
    }

    private fun fetchIds(response: Response) {
        if(_savedPhotosList.value != null)
        {
            val temp = mutableListOf<Int>()
            for (item in _savedPhotosList.value!!)
                temp.add(item.id)

            val newResponse : Response = Response(response.total , response.totalHits , arrayListOf())
            val tempArr = response.hits

            for ((i, item) in response.hits.withIndex())
            {
                if(temp.contains(item.id))
                {
                    val newHit = response.hits[i]
                    newHit.fav = true
                    tempArr[i] = newHit
                }
            }
            newResponse.hits = tempArr
            _photosList.postValue(newResponse)
        }
    }

    private fun getAllPhotosFromAPI() {
        val response = apiRepo.getAllPhotos()
        response.enqueue(object : Callback<Response> {
            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {
                response.body()?.let { fetchIds(it) }
                //_photosList.postValue(response.body())
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                _errorMsg.postValue(t.message)
            }
        })
    }

    private fun getAllPhotosFromDatabase() {
        Thread(Runnable {
            _savedPhotosList.postValue(databaseRepo.getData())
        }).start()
    }

    fun addPhotoToDatabase(hit: HitEntity) {
        Thread(Runnable {
            databaseRepo.insertPhoto(hit)
        }).start()
    }

    fun deletePhotoFromDatabase(hit: HitEntity) {
        Thread(Runnable {
            databaseRepo.deletePhoto(hit)
        }).start()
        getAllPhotosFromDatabase()
    }
}