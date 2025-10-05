package com.unsoed.dessertie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Temukan komponen navbar dari layout berdasarkan ID-nya
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 2. Tambahkan 'pendengar' (listener) untuk setiap item di navbar
        // Kode di dalam blok ini akan dijalankan SETIAP KALI salah satu ikon ditekan
        bottomNavigationView.setOnItemSelectedListener { item ->
            // 3. Logika untuk setiap tombol: periksa ID item yang diklik
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Jika tombol Home ditekan, jangan lakukan apa-apa karena kita sudah di sini
                    true // 'true' berarti event klik sudah ditangani
                }
                R.id.navigation_search -> {
                    // Jika tombol Search ditekan, buat 'surat perintah' (Intent)
                    // untuk pindah ke CategoryActivity
                    val intent = Intent(this, CategoryActivity::class.java)
                    startActivity(intent)
                    // overridePendingTransition(0, 0) // Hapus efek transisi antar activity
                    true
                }
                R.id.navigation_favorite -> {
                    // Nanti kita bisa tambahkan logika untuk pindah ke halaman Favorite
                    // Untuk sekarang, biarkan saja dulu
                    true
                }
                else -> false // Jika ada item lain, abaikan
            }
        }
    }
}

