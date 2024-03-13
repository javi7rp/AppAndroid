package com.example.hospedajetema3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(name="email") val email:String,
    @ColumnInfo(name="password") val password:String
)
