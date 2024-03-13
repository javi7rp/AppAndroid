package com.example.hospedajetema3.models

class Usuario {
    var email: String = ""
        set(value) {
            field = value.trim()
        }
        get() = field

    var password: String = ""
        set(value) {
            field = value
        }
        get() = field

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}