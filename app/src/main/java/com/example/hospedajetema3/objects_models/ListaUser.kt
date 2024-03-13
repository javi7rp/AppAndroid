package com.example.hospedajetema3.objects_models

import com.example.hospedajetema3.models.Usuario

object ListaUser {
    private var usuarios: ArrayList<Usuario> = ArrayList(listOf(Usuario("admin", "123")))

    fun annadirUsuario (nuevoUsuario: Usuario){ this.usuarios.add(nuevoUsuario)}

    fun obtenerUsuario(): ArrayList<Usuario> {return this.usuarios}

}