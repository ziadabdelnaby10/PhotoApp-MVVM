package com.example.photoapp.database

import androidx.room.*
import com.example.photoapp.model.HitEntity

@Dao
interface HitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(hit: HitEntity)

    @Delete
    fun deletePhoto(hit: HitEntity)

    @Query("SELECT * FROM hits")
    fun loadAllPhotos(): List<HitEntity>

}