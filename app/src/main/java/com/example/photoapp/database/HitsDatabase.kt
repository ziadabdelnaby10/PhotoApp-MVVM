package com.example.photoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photoapp.model.HitEntity

@Database(entities = [HitEntity::class], version = 1)
abstract class HitsDatabase : RoomDatabase() {
    abstract fun HitsDao(): HitsDao

    companion object {

        private var INSTANCE: HitsDatabase? = null

        fun getInstance(context: Context) : HitsDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    HitsDatabase::class.java,
                    "hit_database").fallbackToDestructiveMigration().build()
                INSTANCE!!
            }
        }
    }
}