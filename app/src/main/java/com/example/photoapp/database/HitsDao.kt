package com.example.photoapp.database

import androidx.room.*
import com.example.photoapp.model.HitEntity

@Dao
interface HitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(hit: HitEntity)

    @Delete
    suspend fun deletePhoto(hit: HitEntity)

    @Query("SELECT * FROM hits")
    suspend fun loadAllPhotos(): List<HitEntity>

}