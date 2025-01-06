package com.example.crud_berita_user.model

data class RegisterRequest(
    val Username : String,
    val Fullname : String,
    val Email : String,
    val Password : String
)
