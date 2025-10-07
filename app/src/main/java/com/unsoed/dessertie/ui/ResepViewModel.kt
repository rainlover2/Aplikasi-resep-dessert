package com.unsoed.dessertie.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.unsoed.dessertie.data.Resep
import com.unsoed.dessertie.data.ResepRepository
import kotlinx.coroutines.launch

class ResepViewModel(private val repository: ResepRepository) : ViewModel() {

    val semuaResep: LiveData<List<Resep>> = repository.getAllRecipes()

    // Properti untuk menampung hasil pencarian
    private val _searchResults = MutableLiveData<List<Resep>>()
    val searchResults: LiveData<List<Resep>> = _searchResults

    init {
        viewModelScope.launch {
            repository.checkAndPrepopulateDatabase()
        }
    }


    fun searchRecipes(query: String) {
        viewModelScope.launch {

            val results = repository.searchRecipesNonLive(query)
            _searchResults.postValue(results)
        }
    }

    fun getRecipesByCategory(kategori: String): LiveData<List<Resep>> = repository.getRecipesByCategory(kategori)

    fun getFavoriteRecipes() = repository.getFavoriteRecipes()

    fun getRecipeById(id: Int): LiveData<Resep> = repository.getRecipeById(id)

    fun updateFavoriteStatus(resep: Resep) {
        viewModelScope.launch {
            repository.updateResep(resep)
        }
    }
}


class ResepViewModelFactory(private val repository: ResepRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResepViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResepViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}