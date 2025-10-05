package com.unsoed.dessertie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // 1. Temukan komponen navbar dari layout
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 2. Tandai item 'Search' sebagai yang sedang aktif di halaman ini
        bottomNavigationView.selectedItemId = R.id.navigation_search

        // 3. Tambahkan 'pendengar' (listener) untuk setiap item di navbar
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Jika tombol Home ditekan, kembali ke MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    // Hapus efek transisi agar perpindahan terasa mulus
                    overridePendingTransition(0, 0)
                    // Selesaikan activity ini agar tidak menumpuk saat tombol back ditekan
                    finish()
                    true
                }
                R.id.navigation_search -> {
                    // Kita sudah di halaman ini, jadi jangan lakukan apa-apa
                    true
                }
                R.id.navigation_favorite -> {
                    // Nanti kita bisa tambahkan logika untuk pindah ke halaman Favorite
                    true
                }
                else -> false
            }
        }
    }
}
