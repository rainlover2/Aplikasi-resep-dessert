package com.unsoed.dessertie.data

import androidx.lifecycle.LiveData
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
    fun getAllRecipes(): LiveData<List<Resep>>

    @Query("SELECT * FROM tabel_resep WHERE kategori = :kategori")
    fun getRecipesByCategory(kategori: String): LiveData<List<Resep>>

    // PERBAIKAN: Ubah menjadi LiveData agar daftar favorit otomatis update
    @Query("SELECT * FROM tabel_resep WHERE isFavorite = 1")
    fun getFavoriteRecipes(): LiveData<List<Resep>>

    // PERBAIKAN: Ubah menjadi LiveData agar hasil pencarian otomatis update
    @Query("SELECT * FROM tabel_resep WHERE namaResep LIKE '%' || :query || '%'")
    fun searchRecipes(query: String): LiveData<List<Resep>>

    @Query("SELECT * FROM tabel_resep WHERE id = :id")
    fun getRecipeById(id: Int): LiveData<Resep>

    @Query("SELECT * FROM tabel_resep LIMIT 1")
    suspend fun getAnyRecipe(): List<Resep>

    @Query("SELECT * FROM tabel_resep WHERE namaResep LIKE '%' || :query || '%'")
    suspend fun searchRecipesNonLive(query: String): List<Resep>
}