package com.example.hospedajetema3.models

data class Usuario (
    var user: String,
    var pass: String,
) {
    override fun toString(): String {
        return "Usuario(user='$user', pass='$pass')"
    }
}