package com.example.hospedajetema3.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hospedajetema3.data.UsuarioEntityDao
import com.example.hospedajetema3.data.UsuarioEntity

@Database(entities = [UsuarioEntity::class], version = 1, exportSchema = false)
abstract class DatabaseUsuarioEntity : RoomDatabase() {
    abstract fun usuarioEntityDao(): UsuarioEntityDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseUsuarioEntity? = null

        fun getDatabase(context: Context): DatabaseUsuarioEntity {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseUsuarioEntity::class.java,
                    "MY_DATABASE"
                )
                    .addCallback(object : RoomDatabase.Callback() {

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            Log.d("Room", "Base de datos abierta")
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}