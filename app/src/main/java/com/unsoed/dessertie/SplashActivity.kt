package com.unsoed.dessertie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Sembunyikan ActionBar (judul di bagian atas) agar tampilan penuh
        supportActionBar?.hide()

        // Cari tombol "GET STARTED" berdasarkan ID-nya
        val getStartedButton: Button = findViewById(R.id.btn_get_started)

        // Atur listener klik untuk tombol
        getStartedButton.setOnClickListener {
            // Buat Intent untuk berpindah dari SplashActivity ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Selesaikan SplashActivity agar pengguna tidak bisa kembali ke halaman ini
            finish()
        }
    }
}

