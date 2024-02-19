package com.example.hospedajetema3.interfaces

import com.example.hospedajetema3.models.Juego
import com.example.hospedajetema3.objects_models.Respository

interface InterfazDao {
    fun getDataJuego(): List<Juego> = Respository.listJuego
}