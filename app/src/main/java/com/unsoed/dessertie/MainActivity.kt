package com.unsoed.dessertie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.unsoed.dessertie.databinding.ActivityMainBinding
import com.unsoed.dessertie.ui.ImageSliderAdapter
import com.unsoed.dessertie.ui.ResepViewModel
import com.unsoed.dessertie.ui.ResepViewModelFactory
import com.unsoed.dessertie.ui.adapter.ResepGridAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val resepViewModel: ResepViewModel by lazy {
        val factory = ResepViewModelFactory((application as ResepApplication).repository)
        ViewModelProvider(this, factory)[ResepViewModel::class.java]
    }

    private lateinit var resepGridAdapter: ResepGridAdapter

    private lateinit var imageSliderAdapter: ImageSliderAdapter

    private val sliderHandler = Handler(Looper.getMainLooper())
    private lateinit var sliderRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // 1. Setup Banner dengan 4 gambar terpisah
        val bannerImages = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
        )
        // PERBAIKAN: Langsung masukkan daftar gambar ke adapter
        imageSliderAdapter = ImageSliderAdapter(bannerImages)
        binding.imageSlider.adapter = imageSliderAdapter
        TabLayoutMediator(binding.tabLayoutIndicator, binding.imageSlider) { _, _ -> }.attach()
        setupAutoSlide()

        // 2. Setup RecyclerView untuk Menu Populer
        // PERBAIKAN: Tambahkan logika klik favorit saat membuat adapter
        resepGridAdapter = ResepGridAdapter(
            // Perintah saat ikon hati di-klik
            onFavoriteClick = { resep ->
                resepViewModel.updateFavoriteStatus(resep)
            },
            // Perintah saat item gambar/card di-klik
            onItemClick = { resep ->
                val intent = Intent(this, DetailResepActivity::class.java)
                intent.putExtra("EXTRA_RESEP_ID", resep.id)
                startActivity(intent)
            }
        )
        binding.recyclerViewPopuler.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewPopuler.adapter = resepGridAdapter

        // 3. Setup Bottom Navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_search -> {
                    startActivity(Intent(this, CategoryActivity::class.java))
                    true
                }
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

    private fun observeViewModel() {
        resepViewModel.semuaResep.observe(this) { daftarResep ->
            daftarResep?.let {
                // Batasi data untuk grid menjadi 8 item
                resepGridAdapter.setData(it.take(8))
            }
        }
    }

    private fun setupAutoSlide() {
        sliderRunnable = Runnable {
            var currentItem = binding.imageSlider.currentItem
            currentItem++
            if (currentItem >= imageSliderAdapter.itemCount) {
                currentItem = 0
            }
            binding.imageSlider.setCurrentItem(currentItem, true)
        }

        binding.imageSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }
}