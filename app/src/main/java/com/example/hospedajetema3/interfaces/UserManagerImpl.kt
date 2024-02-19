package com.example.hospedajetema3.interfaces

import com.example.hospedajetema3.models.Usuario
import com.example.hospedajetema3.objects_models.ListaUser

class UserManagerImpl : UserManager {
    override fun loginUser(username: String, password: String): Boolean {
        return  ListaUser.verificarUsuario(Usuario(username, password))
    }

    override fun registerUser(username: String, password: String): Boolean {
        ListaUser.annadirUsuario(Usuario(username,password))
        return true
    }
}