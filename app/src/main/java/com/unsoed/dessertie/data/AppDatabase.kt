package com.unsoed.dessertie.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Resep::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun resepDao(): ResepDao

}