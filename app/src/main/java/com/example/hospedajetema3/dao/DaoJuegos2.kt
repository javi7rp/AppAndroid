package com.example.hospedajetema3.dao

import com.example.hospedajetema3.models.Juego
import com.example.hospedajetema3.objects_models.Respository
object DaoJuegos2 {
    val myDao by lazy {
        getDataJuegos()
    }
    private fun getDataJuegos() : List<Juego> = Respository.listJuego
}