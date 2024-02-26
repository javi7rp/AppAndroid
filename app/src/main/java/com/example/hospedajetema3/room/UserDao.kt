package com.example.hospedajetema3.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query(value = "SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUser(username: String, password: String): User?

    @Insert
    fun insert(user: User)
}
