package com.example.hospedajetema3.models

data class Pregunta(
    val pregunta: String,
    val opciones: List<String>,
    val respuestaCorrecta: String
)