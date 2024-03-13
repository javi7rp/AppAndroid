package com.example.hospedajetema3.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.hospedajetema3.data.UsuarioEntity

//android:name=".aplication.UsuarioEntityApplication"
@Dao
interface UsuarioEntityDao {

    @Query("SELECT * FROM usuarios")
    fun getAll(): List<UsuarioEntity>

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :contrasena")
    fun getUsuarioByEmailAndPassword(email: String, contrasena: String): UsuarioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE email = :email ")
    fun getUsuarioByEmail(email: String): UsuarioEntity

    @Query("DELETE FROM usuarios")
    fun borrarUsuarioPorId()

}
