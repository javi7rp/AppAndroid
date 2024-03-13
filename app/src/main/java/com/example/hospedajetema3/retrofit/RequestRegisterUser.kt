package com.example.hospedajetema3.retrofit

data class RequestRegisterUser(
    var email: String,
    var password: String,
    var nombre: String,
    var imagen: String? = null,
    var disponible:Int = 1
)
