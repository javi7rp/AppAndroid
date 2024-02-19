package com.example.hospedajetema3.models

import androidx.room.Database
import androidx.room.RoomDatabase

// AppDatabase.kt
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
