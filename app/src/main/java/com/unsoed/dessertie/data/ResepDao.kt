package com.unsoed.dessertie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ResepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(resep: List<Resep>)

    @Update
    suspend fun updateResep(resep: Resep)

    @Query("SELECT * FROM tabel_resep ORDER BY namaResep ASC")
    fun getAllRecipes(): List<Resep>

    @Query("SELECT * FROM tabel_resep WHERE kategori = :kategori")
    fun getRecipesByCategory(kategori: String): List<Resep>

    @Query("SELECT * FROM tabel_resep WHERE isFavorite = 1")
    fun getFavoriteRecipes(): List<Resep>

    @Query("SELECT * FROM tabel_resep WHERE namaResep LIKE '%' || :query || '%'")
    fun searchRecipes(query: String): List<Resep>

}