package com.unsoed.dessertie

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.dessertie.databinding.ActivityCategoryBinding
import com.unsoed.dessertie.DetailResepActivity
import com.unsoed.dessertie.ui.Kategori
import com.unsoed.dessertie.ui.KategoriAdapter
import com.unsoed.dessertie.ui.ResepViewModel
import com.unsoed.dessertie.ui.ResepViewModelFactory
import com.unsoed.dessertie.ui.adapter.ResepGridAdapter

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var resepGridAdapter: ResepGridAdapter

    private val resepViewModel: ResepViewModel by lazy {
        val factory = ResepViewModelFactory((application as ResepApplication).repository)
        ViewModelProvider(this, factory)[ResepViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapters()
        setupCategoryList()
        setupSearch()
        setupBottomNavigation()
    }

    private fun setupAdapters() {
        // Siapkan adapter untuk hasil pencarian (Resep)
        resepGridAdapter = ResepGridAdapter(
            onFavoriteClick = { resep -> resepViewModel.updateFavoriteStatus(resep) },
            onItemClick = { resep ->
                val intent = Intent(this, DetailResepActivity::class.java)
                intent.putExtra("EXTRA_RESEP_ID", resep.id)
                startActivity(intent)
            }
        )
        binding.rvSearchResults.layoutManager = GridLayoutManager(this, 2)
        binding.rvSearchResults.adapter = resepGridAdapter
    }

    private fun setupCategoryList() {
        val daftarKategori = listOf(
            Kategori("Es Krim", R.drawable.eskrim),
            Kategori("Minuman", R.drawable.minuman),
            Kategori("Cake dan Pastry", R.drawable.cake_dan_pastry),
            Kategori("Pudding dan Custard", R.drawable.pudding_dan_custard),
            Kategori("Hidangan Buah", R.drawable.hidangan_buah),
            Kategori("Cemilan", R.drawable.cemilan)
        )
        val kategoriAdapter = KategoriAdapter(daftarKategori)
        binding.rvKategori.layoutManager = LinearLayoutManager(this)
        binding.rvKategori.adapter = kategoriAdapter
    }

    private fun setupSearch() {
        // Amati LiveData dari ViewModel
        resepViewModel.searchResults.observe(this) { results ->
            resepGridAdapter.setData(results)
        }

        // Tambahkan listener pada EditText
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if (query.isNotBlank()) {
                    binding.rvKategori.visibility = View.GONE
                    binding.tvTitle.visibility = View.GONE
                    binding.rvSearchResults.visibility = View.VISIBLE
                    resepViewModel.searchRecipes(query)
                } else {
                    binding.rvKategori.visibility = View.VISIBLE
                    binding.tvTitle.visibility = View.VISIBLE
                    binding.rvSearchResults.visibility = View.GONE
                }
            }
        })
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.navigation_search
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.navigation_search -> true
                R.id.navigation_favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}