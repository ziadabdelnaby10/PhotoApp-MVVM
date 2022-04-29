package com.example.photoapp.repo

import com.example.photoapp.database.HitsDao
import com.example.photoapp.model.HitEntity

class DatabaseRepo(private val dao : HitsDao) {
    fun getData() : List<HitEntity> = dao.loadAllPhotos()

    fun insertPhoto(hit : HitEntity) = dao.insertPhoto(hit)

    fun deletePhoto(hit: HitEntity) = dao.deletePhoto(hit)
}