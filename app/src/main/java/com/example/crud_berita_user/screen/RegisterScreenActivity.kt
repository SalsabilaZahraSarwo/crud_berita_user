package com.example.crud_berita_user.screen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crud_berita_user.ListMahasiswaActivity
import com.example.crud_berita_user.R
import com.example.crud_berita_user.model.RegisterResponse
import com.example.crud_berita_user.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_screen)

        val etUsername: EditText = findViewById(R.id.etUsername)
        val etFullname: EditText = findViewById(R.id.etFullname)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        // Set button register ketika di klik
        btnRegister.setOnClickListener {
            // Get data dari widget
            val username = etUsername.text.toString().trim()
            val fullname = etFullname.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validasi input
            if (username.isEmpty() || fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register user melalui API
            try {
                ApiClient.retrofit.registerUser(username, password, fullname, email)
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (response.isSuccessful) {
                                val message = response.body()?.message ?: "Pendaftaran berhasil"
                                Toast.makeText(
                                    this@RegisterScreenActivity,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Beralih ke halaman RowUserActivity
                                val toRowUser = Intent(this@RegisterScreenActivity, ListMahasiswaActivity::class.java)
                                startActivity(toRowUser)
                                finish() // Menutup activity saat ini
                            } else {
                                val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                                Log.e("Register Error", errorMessage)
                                Toast.makeText(
                                    this@RegisterScreenActivity,
                                    "Register gagal: $errorMessage",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Log.e(TAG, "API call failed: ${t.message}", t)
                            Toast.makeText(
                                this@RegisterScreenActivity,
                                "Terjadi kesalahan: ${t.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterScreenActivity,
                    "Error occurred: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "Error occurred: ${e.message}", e)
            }
        }

        // Menambahkan padding untuk insets sistem
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
