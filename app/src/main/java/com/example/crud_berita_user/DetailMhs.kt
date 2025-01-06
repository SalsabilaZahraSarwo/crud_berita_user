package com.example.crud_berita_user

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailMhs : AppCompatActivity() {
    private lateinit var usernameDetail : TextView
    private lateinit var fullnameDetail : TextView
    private lateinit var emailDetail : TextView
    private lateinit var tglDetail : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_mhs)

        usernameDetail = findViewById(R.id.usernameDetail)
        fullnameDetail = findViewById(R.id.fullnameDetail)
        emailDetail = findViewById(R.id.emailDetail)
        tglDetail = findViewById(R.id.tanggalDetail)

        val username = intent.getStringExtra("username")
        val fullname = intent.getStringExtra("fullname")
        val email = intent.getStringExtra("email")
        val tglDaftar = intent.getStringExtra("tgl_daftar")

        usernameDetail.text = username
        fullnameDetail.text = fullname
        emailDetail.text = email
        tglDetail.text = tglDaftar

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}