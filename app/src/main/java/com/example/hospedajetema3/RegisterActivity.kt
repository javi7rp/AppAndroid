package com.example.hospedajetema3

import android.app.Activity
import android.app.AlertDialog
import android.app.Instrumentation
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hospedajetema3.data.DatabaseUsuarioEntity
import com.example.hospedajetema3.data.UsuarioEntityDao
import com.example.hospedajetema3.retrofit.RequestRegisterUser
import com.example.hospedajetema3.retrofit.RetrofitModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class RegisterActivity : AppCompatActivity() {

    private lateinit var dao: UsuarioEntityDao
    private lateinit var database: DatabaseUsuarioEntity
    private lateinit var etEmail: EditText
    private lateinit var etContraseña: EditText
    private lateinit var etNombre: EditText
    private lateinit var imageView: ImageView
    private lateinit var btnCancelar: Button
    private lateinit var btnRegistrar: Button
    private lateinit var fotoButton: Button
    private lateinit var cargarFotoButton: Button
    private var bitmap: Bitmap? = null
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var startForGallery: ActivityResultLauncher<Intent>

    private val CAMERA_REQUEST_CODE = 123
    private val RC_PICK_IMAGE = 125

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        database = DatabaseUsuarioEntity.getDatabase(applicationContext);
        dao = database.usuarioEntityDao()
        etEmail = findViewById(R.id.etEmail)
        etContraseña = findViewById(R.id.etContraseña)
        etNombre = findViewById(R.id.etNombre)
        imageView = findViewById(R.id.imageView)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        fotoButton = findViewById(R.id.fotoBoton)
        cargarFotoButton = findViewById(R.id.cargarFoto)


        fotoButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startForResult.launch(takePictureIntent)
        }
        cargarFotoButton.setOnClickListener {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startForGallery.launch(pickPhoto)
        }

        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                handleImageCaptureResult(result)
            }

        startForGallery =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                handleImageGalleryResult(result)
            }

        btnRegistrar.setOnClickListener {
            val email = etEmail.text.toString()
            val contraseña = etContraseña.text.toString()
            val nombre = etNombre.text.toString()

            if (contraseña.isNotEmpty() && nombre.isNotEmpty() && email.isNotEmpty()) {

                GlobalScope.launch(Dispatchers.IO) {
                    val imagenBase64 = bitmap?.let { bitmapToBase64(it) }
                    val response = RetrofitModule.apiService.registro(
                        RequestRegisterUser(
                            email,
                            contraseña,
                            nombre,
                            imagenBase64 // Incluye la imagen en formato Base64 en la solicitud
                        )
                    )

                    if (response.isSuccessful && response.body()?.result == "ok") {
                        if (response.body()?.insert_id == 0) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "EMAIL YA REGISTRADO",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "REGISTRADO SATISFACTORIAMENTE",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            Thread.sleep(2000)
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "NO SE PUDO REALIZAR EL REGISTRO",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Por favor, completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private val REQUEST_CAMERA_PERMISSION = 100
    private val REQUEST_GALLERY_PERMISSION = 102

    private fun checkCameraPermission(isOpeningCamera: Boolean) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                if (isOpeningCamera) REQUEST_CAMERA_PERMISSION else REQUEST_GALLERY_PERMISSION
            )
        }
    }


    private fun handleImageCaptureResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            data?.let {
                val imageBitmap = it.extras?.get("data") as Bitmap
                imageView.setImageBitmap(imageBitmap)
                bitmap = imageBitmap
            }
        }
    }

    private fun handleImageGalleryResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            data?.let {
                val imageUri: Uri? = it.data
                val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                imageView.setImageBitmap(imageBitmap)
                bitmap = imageBitmap
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val imagenbase64 =Base64.encodeToString(byteArray, Base64.DEFAULT)
        return "data:image/PNG;base64,$imagenbase64"

    }
}

