package com.example.hospedajetema3

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.room.Room
import com.example.hospedajetema3.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class RegisterActivity : AppCompatActivity()  {

    private lateinit var etUsuario: EditText
    private lateinit var etContraseña: EditText
    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var etEmail: EditText
    private lateinit var etInstagram: EditText
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var imageView: ImageView
    private lateinit var btnCancelar: Button
    private lateinit var btnRegistrar: Button

    private val CAMERA_REQUEST_CODE = 123
    private val RC_PICK_IMAGE = 125

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsuario = findViewById(R.id.etUsuario)
        etContraseña = findViewById(R.id.etContraseña)
        etNombre = findViewById(R.id.etNombre)
        etEdad = findViewById(R.id.etEdad)
        etEmail = findViewById(R.id.etEmail)
        etInstagram = findViewById(R.id.etInstagram)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        imageView = findViewById(R.id.imageView)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnRegistrar = findViewById(R.id.btnRegistrar)


        btnSeleccionarImagen.setOnClickListener {
            showImageSourceDialog()
        }

        btnRegistrar.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val contraseña = etContraseña.text.toString()
            val nombre = etNombre.text.toString()
            val edad = etEdad.text.toString().toInt()
            val email = etEmail.text.toString()
            val instagram = etInstagram.text.toString()

            if (usuario.isNotEmpty() && contraseña.isNotEmpty() && nombre.isNotEmpty() &&
                edad > 0 && email.isNotEmpty() && instagram.isNotEmpty()) {

            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_PICK_IMAGE)
    }

    private fun showImageSourceDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una fuente de imagen")
        builder.setItems(arrayOf("Cámara", "Galería")) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    // La foto ha sido tomada, y 'data' contiene la información de la foto
                    val photo: Bitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(photo)
                }
            }
            RC_PICK_IMAGE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Imagen seleccionada desde la galería
                    val selectedImage: Uri? = data.data
                    selectedImage?.let {
                        imageView.setImageURI(selectedImage)
                    }
                }
            }
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
