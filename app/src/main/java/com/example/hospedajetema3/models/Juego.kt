package com.example.hospedajetema3.models

class Juego (
    var id: String,
    var name: String,
    var plataforma: String,
    var anno: String,
    var nota: String,
    var image: String,
    //var isFav: Boolean = false
) {
    override fun toString(): String {
        return "Juego(id='$id',  name='$name', plataforma='$plataforma', anno='$anno', nota='$nota', image='$image')" //, fav='$isFav')"
    }
}