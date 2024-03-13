package com.example.hospedajetema3.models

import android.content.Context
import android.content.SharedPreferences

class Preferencias (context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("PrefenciasCompartidas", Context.MODE_PRIVATE)

    fun guardarUsuarioToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.putBoolean("logueado", true)
        editor.apply()
    }

    fun obtenerTokenUsuario(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun logueado(): Boolean {
        return sharedPreferences.getBoolean("logueado", false)
    }

    fun borrarPreferencias() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}