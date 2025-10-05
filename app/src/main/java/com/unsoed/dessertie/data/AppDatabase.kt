package com.unsoed.dessertie.data

import androidx.room.Database
import androidx.room.RoomDatabase

// Anotasi @Database untuk menandai class ini sebagai database Room
// entities = berisi daftar semua Entity (tabel) yang ada di database ini
// version = versi database, mulai dari 1. Jika ada perubahan struktur, nomor ini harus naik.
@Database(entities = [Resep::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Fungsi ini digunakan agar bagian lain dari aplikasi bisa mengakses DAO
    abstract fun resepDao(): ResepDao

}