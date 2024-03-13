package com.example.hospedajetema3.retrofit

import com.example.hospedajetema3.models.Juego
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class ResponseListJuegos(
    @SerializedName("result")
    @Expose
    val result : String,

    @SerializedName("listJuegos")
    @Expose
    val listJuegos : List<Juego>,

    @SerializedName("id")
    @Expose
    val id : Int,

    @SerializedName("id_usuario")
    @Expose
    val id_usurio : Int,

    @SerializedName("nombre")
    @Expose
    val nombre : String,

    @SerializedName("plataforma")
    @Expose
    val plataforma : String,

    @SerializedName("anno")
    @Expose
    val anno : String,

    @SerializedName("nota")
    @Expose
    val nota : String,

    @SerializedName("imagen")
    @Expose
    val imagen : String

)
