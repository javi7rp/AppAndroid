package com.example.hospedajetema3

/*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hospedajetema3.dialogues.DialogCallback
import com.example.hospedajetema3.models.Usuario
import com.example.hospedajetema3.objects_models.ListaUser

class LoginActivity : AppCompatActivity(), DialogCallback {

    private lateinit var userEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var iniciarSesionButton: Button
    private lateinit var registrarseButton: Button
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio_sesion)


        sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)

        if (isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            initViews()
            setupListeners()
        }
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun initViews() {
        userEditText = findViewById(R.id.user)
        passwordEditText = findViewById(R.id.password)
        iniciarSesionButton = findViewById(R.id.iniciarSesion)
        registrarseButton = findViewById(R.id.registrarse)
    }

    private fun setupListeners() {

        iniciarSesionButton.setOnClickListener {
            val user = userEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (ListaUser.verificarUsuario(Usuario(user,password))){
                saveUserCredenciales(user, password)
                setLoggedIn(true)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this, "USUARIO NO REGISTRADO o CAMPOS NO RELLENADOS", Toast.LENGTH_SHORT).show()
            }
        }
        registrarseButton.setOnClickListener {
            mostrarDialogoRegistro()
        }
    }


    private fun saveUserCredenciales(user: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USER", user)
        editor.apply()
    }

    private fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    private fun mostrarDialogoRegistro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro De Usuario")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val textUser = TextView(this)
        textUser.text = "Usuario:"
        layout.addView(textUser)

        val inputUser = EditText(this)
        layout.addView(inputUser)

        val textPass = TextView(this)
        textPass.text = "Contraseña:"
        layout.addView(textPass)

        val inputPass = EditText(this)
        inputPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(inputPass)

        val textPass2 = TextView(this)
        textPass2.text = "Confirmar Contraseña:"
        layout.addView(textPass2)

        val inputPass2 = EditText(this)
        inputPass2.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        layout.addView(inputPass2)

        builder.setView(layout)

        builder.setPositiveButton("Registrarse") { _, _ ->
            val user = inputUser.text.toString()
            val pass = inputPass.text.toString()
            val pass2 = inputPass2.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty() && pass2.isNotEmpty()) {
                if (pass.equals(pass2)){
                    onRegisterClicked(user,pass)
                }else{
                    Toast.makeText(this, "INTRODUCE LAS CONTRASEÑA CORRECTAMENTE", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    override fun onRegisterClicked(user: String, pass: String) {
       ListaUser.annadirUsuario(Usuario(user, pass))
        Toast.makeText(this, "REGISTADO CORRECTAMENTE User: $user - Password: $pass", Toast.LENGTH_SHORT).show()
    }




} */

//----------------------------------ROOM-------------------------------

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var userEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var iniciarSesionButton: Button
    private lateinit var registrarseButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio_sesion)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        userEditText = findViewById(R.id.user)
        passwordEditText = findViewById(R.id.password)
        iniciarSesionButton = findViewById(R.id.iniciarSesion)
        registrarseButton = findViewById(R.id.registrarse)
    }

    private fun setupListeners() {
        iniciarSesionButton.setOnClickListener {
            val email = userEditText.text.toString()
            val password = passwordEditText.text.toString()

            val endpointUrl = "http://localhost/api-juegos/endp/auth"

            // Llamar a la tarea AsyncTask para enviar la solicitud POST
            val httpPostTask = HttpPostTask(object : HttpPostTask.OnTaskCompleted {
                override fun onTaskCompleted(result: String) {
                    // Aquí manejas la respuesta del servidor
                    if (result.isNotEmpty()) {
                        // Verificar la respuesta del servidor
                        if (result.contains("token")) {
                            // Si la respuesta contiene "token", el inicio de sesión fue exitoso
                            // Guardar el token en SharedPreferences u otro almacenamiento seguro
                            sharedPreferences.edit().putString("token", result).apply()

                            // Redirigir a la actividad principal u otra actividad que desees
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish() // Terminar la actividad actual
                        } else {
                            // Si la respuesta no contiene "token", muestra un mensaje de error
                            Toast.makeText(
                                this@LoginActivity,
                                "Inicio de sesión fallido. Verifica tus credenciales.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Si no hay respuesta del servidor, muestra un mensaje de error
                        Toast.makeText(
                            this@LoginActivity,
                            "Error de conexión. Inténtalo de nuevo más tarde.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })

            // Ejecutar la tarea AsyncTask para realizar la solicitud POST
            httpPostTask.execute(endpointUrl, email, password)
        }

        registrarseButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}

