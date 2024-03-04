package com.example.hospedajetema3.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.hospedajetema3.GameActivity
import com.example.hospedajetema3.R
import com.example.hospedajetema3.room.*

//import pub.devrel.easypermissions.EasyPermissions

class FragmentPerfil : Fragment() {

    private lateinit var ivProfilePicture: ImageView
    private lateinit var btnEditFoto: Button

    private lateinit var btnPreguntas: ImageButton
    private lateinit var btnInstagram: ImageButton
    private lateinit var txtInstaUser: TextView

    private lateinit var btnEditarPerfil: Button
    private lateinit var txtUserName: TextView
    private lateinit var txtUserAge: TextView
    private lateinit var txtUserEmail: TextView

    private lateinit var editTextNuevoNombre: EditText
    private lateinit var editTextNuevaEdad: EditText
    private lateinit var editTextNuevoEmail: EditText
    private lateinit var editTextNuevoInsta: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancel: Button
    private lateinit var btnBorrarDatos: Button

    private lateinit var sharedPreferences: SharedPreferences

    //private lateinit var sharedPreferences: SharedPreferences
    private lateinit var db: AppDatabase

    private lateinit var txtRecordPreguntas: TextView
    var isResultadoActualizado = false

    private val CAMERA_REQUEST_CODE = 123
    private val RC_CAMERA_STORAGE_PERM = 124
    private val RC_PICK_IMAGE = 125

    private val perms = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "my-database").build()
        sharedPreferences = requireActivity().getSharedPreferences("datosUser", Context.MODE_PRIVATE)


        btnPreguntas = view.findViewById(R.id.btnPreguntas)
        txtRecordPreguntas = view.findViewById(R.id.recordPreguntas)
        btnInstagram = view.findViewById(R.id.btnInstagram)
        txtInstaUser = view.findViewById(R.id.txtInstaUser)
        txtUserName = view.findViewById(R.id.txtUserName)
        txtUserAge = view.findViewById(R.id.txtUserAge)
        txtUserEmail = view.findViewById(R.id.txtUserEmail)

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture)

        updateUIWithUserData()


        // Configura el listener para el clic del botón
        btnInstagram.setOnClickListener {
            val instagramUser = txtInstaUser.text.toString()
            val instagramUrl = "https://www.instagram.com/$instagramUser/"

            try {
                // Intenta abrir la URL en la aplicación de Instagram
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("instagram://user?username=$instagramUser")
                intent.setPackage("com.instagram.android")
                startActivity(intent)
            } catch (e: Exception) {
                // Si no se puede abrir en Instagram, abre en el navegador web
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
                startActivity(intent)
            }
        }

        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil)

        btnEditarPerfil.setOnClickListener {
            // Llama al método para mostrar el diálogo
            mostrarDialogoEditarPerfil()
        }

        btnEditFoto = view.findViewById(R.id.btnEditFoto)

        btnEditFoto.setOnClickListener {
            checkCameraAndStoragePermissions()
        }

        btnPreguntas.setOnClickListener {
            val intent = Intent(requireContext(), GameActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun updateUIWithUserData() {
        // Obtiene el nombre de usuario y la contraseña del usuario actual
        val user: String = sharedPreferences.getString("USER", "") ?: ""
        val password: String = sharedPreferences.getString("PASSWORD", "") ?: ""

        // Obtiene los datos del usuario desde la base de datos
        val currentUser = db.userDao().getUser(user, password)

        // Verifica si el usuario no es nulo antes de actualizar la interfaz de usuario
        currentUser?.let {
            txtUserName.text = currentUser.username
            txtUserAge.text = currentUser.edad.toString()
            txtUserEmail.text = currentUser.email
            txtInstaUser.text = currentUser.instagram
            val bitmap = currentUser.image?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
            ivProfilePicture.setImageBitmap(bitmap)
        }
    }

    private fun mostrarDialogoEditarPerfil() {
        val dialogView = Dialog(requireContext())
        dialogView.setContentView(R.layout.dialog_editar_perfil)

        // Encuentra las vistas en el diseño del diálogo
        editTextNuevoNombre = dialogView.findViewById(R.id.editTextNuevoNombre)
        editTextNuevaEdad = dialogView.findViewById(R.id.editTextNuevaEdad)
        editTextNuevoEmail = dialogView.findViewById(R.id.editTextNuevoEmail)
        editTextNuevoInsta = dialogView.findViewById(R.id.editTextInstaUser)
        btnGuardar = dialogView.findViewById(R.id.btnGuardar)
        btnCancel = dialogView.findViewById(R.id.btnCancel)
        btnBorrarDatos = dialogView.findViewById(R.id.btnBorrarDatos)



        editTextNuevoNombre.setText(txtUserName.text)
        editTextNuevaEdad.setText(txtUserAge.text)
        editTextNuevoEmail.setText(txtUserEmail.text)
        editTextNuevoInsta.setText(txtInstaUser.text)

        btnBorrarDatos.setOnClickListener {
            editTextNuevoNombre.setText("")
            editTextNuevaEdad.setText("")
            editTextNuevoEmail.setText("")
            editTextNuevoInsta.setText("")
        }

        // Configura el diálogo
        btnGuardar.setOnClickListener{
                val nuevoNombre = editTextNuevoNombre.text.toString()
                val nuevaEdad = editTextNuevaEdad.text.toString()
                val nuevoEmail = editTextNuevoEmail.text.toString()
                val nuevoInsta = editTextNuevoInsta.text.toString()


                actualizarDatosPerfil(nuevoNombre, nuevaEdad, nuevoEmail, nuevoInsta)
            dialogView.dismiss()
            }

        btnCancel.setOnClickListener{
            dialogView.dismiss()
        }

        // Muestra el diálogo
        dialogView.show()
    }
    private fun actualizarDatosPerfil(nuevoNombre: String, nuevaEdad: String, nuevoEmail: String, nuevoInsta: String) {
        // Aquí puedes actualizar las vistas con los nuevos datos
        // Por ejemplo, puedes actualizar los TextViews con los nuevos valores
        txtUserName.text = nuevoNombre
        txtUserAge.text = nuevaEdad
        txtUserEmail.text = nuevoEmail
        txtInstaUser.text = nuevoInsta
    }
    fun actualizarResultado(contAciertos: Int) {
        if (!isResultadoActualizado){
            txtRecordPreguntas.visibility = View.VISIBLE
            txtRecordPreguntas.setText("PRIMER INTENTO: $contAciertos/10")
            isResultadoActualizado = true
        }

    }



    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    // La foto ha sido tomada, y 'data' contiene la información de la foto
                    val photo: Bitmap = data?.extras?.get("data") as Bitmap
                    // Muestra la foto en el ImageView
                    ivProfilePicture.setImageBitmap(photo)
                }
            }
            RC_PICK_IMAGE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Imagen seleccionada desde la galería
                    val selectedImage: Uri? = data.data
                    val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                    // Muestra la imagen en el ImageView
                    ivProfilePicture.setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_PICK_IMAGE)
    }

    private fun checkCameraAndStoragePermissions() {
        val perms = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
/*
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
            // Tienes permisos, abre la cámara
            showImageSourceDialog()
            Log.d("IIIIIIIIIIIII","abre dialogo")
        } else {
            // No tienes permisos, solicita permisos
            Log.d("IIIIIIIIIIIII","no tiene permisos")
            requestCameraAndStoragePermissions()
        }

 */
    }

    private fun requestCameraAndStoragePermissions() {
        Log.d("IIIIIII", "Solicitando permisos de cámara y almacenamiento...")
        requestPermissions(perms, RC_CAMERA_STORAGE_PERM)
    }

    private fun showImageSourceDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Selecciona una fuente de imagen")
        builder.setItems(arrayOf("Cámara", "Galería")) { _, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.create().show()
    }

}