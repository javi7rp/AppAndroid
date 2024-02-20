package com.example.hospedajetema3.interfaces

import javax.inject.Inject

interface MyInyect {
    fun doSomething()
}

class MyInyectImpl @Inject constructor() : MyInyect{
    override fun doSomething() {
        println("BOTON PULSADO")
    }
}


