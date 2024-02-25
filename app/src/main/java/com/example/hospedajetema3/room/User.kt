package com.example.hospedajetema3.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val username: String,
    val password: String
)
