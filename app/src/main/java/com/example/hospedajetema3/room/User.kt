package com.example.hospedajetema3.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String,
    val nombre: String,
    val edad: Int,
    val email: String,
    val instagram: String,
    val image: ByteArray?
)

