package com.example.photoapp.repo

import com.example.photoapp.database.HitsDao
import com.example.photoapp.model.HitEntity

class DatabaseRepo(private val dao : HitsDao) {
    suspend fun getData() : List<HitEntity> = dao.loadAllPhotos()

    suspend fun insertPhoto(hit : HitEntity) = dao.insertPhoto(hit)

    suspend fun deletePhoto(hit: HitEntity) = dao.deletePhoto(hit)
}