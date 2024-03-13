package com.example.hospedajetema3.models

class Juego (
    var id: String,
    var nombre: String,
    var plataforma: String,
    var anno: String,
    var nota: String,
    var imagen: String,
) {
    override fun toString(): String {
        return "Juego(id='$id', nombre='$nombre', plataforma='$plataforma', anno='$anno', nota='$nota', imagen='$imagen')"
    }
}