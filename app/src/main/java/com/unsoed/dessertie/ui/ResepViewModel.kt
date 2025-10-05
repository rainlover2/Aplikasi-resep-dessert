package com.unsoed.dessertie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.unsoed.dessertie.data.ResepRepository
import kotlinx.coroutines.launch

class ResepViewModel(private val repository: ResepRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.checkAndPrepopulateDatabase()
        }
    }

    // Fungsi-fungsi yang dipanggil oleh UI
    fun getAllRecipes() = repository.getAllRecipes()
    fun searchRecipes(query: String) = repository.searchRecipes(query)
    // ..........
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