package com.unsoed.dessertie.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ResepRepository(private val resepDao: ResepDao, private val context: Context) {

    // Fungsi untuk mengecek dan mengisi data awal jika database kosong
    suspend fun checkAndPrepopulateDatabase() {
        if (resepDao.getAllRecipes().isEmpty()) {
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

    // Di sini Anda bisa menambahkan fungsi lain untuk berinteraksi dengan DAO
    fun getAllRecipes() = resepDao.getAllRecipes()
    fun searchRecipes(query: String) = resepDao.searchRecipes(query)
    // ...dan seterusnya
}