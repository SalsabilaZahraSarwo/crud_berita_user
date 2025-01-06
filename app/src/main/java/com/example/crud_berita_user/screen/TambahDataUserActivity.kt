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
import com.example.crud_berita_user.model.RegisterRequest
import com.example.crud_berita_user.model.RegisterResponse
import com.example.crud_berita_user.service.ApiClient
import retrofit2.Callback
import retrofit2.Response

class TambahDataUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_data_user)

        val etUsername : EditText = findViewById(R.id.etUsername)
        val etFullname : EditText = findViewById(R.id.etFullName)
        val etEmail : EditText = findViewById(R.id.etEmail)
        val etPassword : EditText = findViewById(R.id.etPassword)
        val button : Button = findViewById(R.id.button)

        button.setOnClickListener(){
            val nUsername = etUsername.text.toString()
            val nFullName = etFullname.text.toString()
            val nPassword = etPassword.text.toString()
            val nEmail = etEmail.text.toString()

            val registerrequest = RegisterRequest(nUsername, nFullName, nPassword, nEmail)
            try {
                ApiClient.retrofit.registerUser(nUsername, nFullName, nPassword,nEmail).enqueue(
                    object  : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: retrofit2.Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (response.isSuccessful){
                                Toast.makeText(this@TambahDataUserActivity, response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                val toregister = Intent(this@TambahDataUserActivity, ListMahasiswaActivity::class.java)
                                startActivity(toregister)
                                finish()
                            }else{
                                val errorMessage = response.errorBody()?.string()?: "unknow Error"
                                Log.e("Register Error", errorMessage)
                                Toast.makeText(
                                    this@TambahDataUserActivity,
                                    "Register failure",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(
                            call: retrofit2.Call<RegisterResponse>, t: Throwable ) {
                            Toast.makeText(
                                this@TambahDataUserActivity, t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }catch (e: Exception){
                Toast.makeText(
                    this@TambahDataUserActivity, "error occured : ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "Error occured : ${e.message}", e)

            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}