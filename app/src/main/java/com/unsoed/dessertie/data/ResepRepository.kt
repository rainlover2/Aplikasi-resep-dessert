package com.unsoed.dessertie.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ResepRepository(private val resepDao: ResepDao, private val context: Context) {

    // Fungsi ini sudah benar
    suspend fun checkAndPrepopulateDatabase() {
        if (resepDao.getAnyRecipe().isEmpty()) {
            try {
                val jsonString = context.assets.open("recipes.json").bufferedReader().use { it.readText() }
                val resepType = object : TypeToken<List<Resep>>() {}.type
                val resepList: List<Resep> = Gson().fromJson(jsonString, resepType)
                resepDao.insertAll(resepList)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // Semua fungsi di bawah ini hanya meneruskan perintah ke DAO
    fun getAllRecipes() = resepDao.getAllRecipes()

    fun searchRecipes(query: String) = resepDao.searchRecipes(query)

    fun getRecipeById(id: Int) = resepDao.getRecipeById(id)

    fun getRecipesByCategory(kategori: String) = resepDao.getRecipesByCategory(kategori)

    fun getFavoriteRecipes() = resepDao.getFavoriteRecipes()

    suspend fun updateResep(resep: Resep) {
        resepDao.updateResep(resep)
    }

    suspend fun searchRecipesNonLive(query: String): List<Resep> {
        return resepDao.searchRecipesNonLive(query)
    }
}