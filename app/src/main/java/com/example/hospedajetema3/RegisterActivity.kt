package com.example.hospedajetema3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.hospedajetema3.room.AppDatabase
import com.example.hospedajetema3.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var userEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registrarButton: Button
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userEditText = findViewById(R.id.register_user)
        passwordEditText = findViewById(R.id.register_password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        registrarButton = findViewById(R.id.registrar)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my-database").build()

        registrarButton.setOnClickListener {
            val user = userEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (user.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    registerUser(user, password)
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(user: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val existingUser = db.userDao().getUser(user, password)
            if (existingUser == null) {
                db.userDao().insert(User(username = user, password = password))
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}