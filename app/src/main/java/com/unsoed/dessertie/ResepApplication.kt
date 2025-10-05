package com.unsoed.dessertie

import android.app.Application
import androidx.room.Room
import com.unsoed.dessertie.data.AppDatabase
import com.unsoed.dessertie.data.ResepRepository

class ResepApplication : Application() {
    private val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-resep"
        ).build()
    }

    val repository by lazy {
        ResepRepository(database.resepDao(), this)
    }
}