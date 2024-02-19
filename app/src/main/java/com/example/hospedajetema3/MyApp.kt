package com.example.hospedajetema3

import android.app.Application
import androidx.room.Room
import com.example.hospedajetema3.models.AppDatabase

class MyApp : Application() {

    // Declarar una variable para la base de datos
    lateinit var appDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()

        // Inicializar la base de datos Room
        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my-database-name")
            .build()
    }
}
