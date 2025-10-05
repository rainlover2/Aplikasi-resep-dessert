package com.unsoed.dessertie.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity memberitahu Room bahwa class ini adalah sebuah tabel di database.
// tableName adalah nama tabelnya.
@Entity(tableName = "tabel_resep")
data class Resep(
    // @PrimaryKey menandakan bahwa 'id' adalah kunci unik untuk setiap baris data.
    // autoGenerate = true berarti Room akan membuatkan id ini secara otomatis.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Ini adalah kolom-kolom lain di dalam tabelmu.
    val namaResep: String,
    val bahan: String,
    val langkahMemasak: String,
    val namaGambar: String, // Hanya nama filenya saja, misal: "cupcake_blueberry"
    val kategori: String,
    var isFavorite: Boolean = false // Status untuk fitur favorit
)
