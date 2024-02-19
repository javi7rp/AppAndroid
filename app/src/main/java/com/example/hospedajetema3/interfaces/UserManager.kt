package com.example.hospedajetema3.interfaces

interface UserManager {
    fun loginUser(username: String, password:String): Boolean
    fun registerUser(username: String, password: String): Boolean
}