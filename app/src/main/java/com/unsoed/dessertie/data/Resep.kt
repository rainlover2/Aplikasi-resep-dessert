package com.unsoed.dessertie.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabel_resep")
data class Resep(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaResep: String,
    val bahan: String,
    val langkahMemasak: String,
    val namaGambar: String,
    val kategori: String,
    var isFavorite: Boolean = false
)