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

//import pub.devrel.easypermissions.EasyPermissions

class FragmentPerfil : Fragment() {

    private lateinit var ivProfilePicture: ImageView

    private lateinit var btnPreguntas: ImageButton
    private lateinit var btnInstagram: ImageButton
    private lateinit var txtInstaUser: TextView

    private lateinit var btnEditarPerfil: Button
    private lateinit var txtUserName: TextView
    private lateinit var txtUserEmail: TextView

    private lateinit var editTextNuevoEmail: EditText
    private lateinit var editTextNuevoNombre: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancel: Button
    private lateinit var btnBorrarDatos: Button



    private lateinit var txtRecordPreguntas: TextView
    var isResultadoActualizado = false

    private val CAMERA_REQUEST_CODE = 123
    private val RC_PICK_IMAGE = 125



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        btnPreguntas = view.findViewById(R.id.btnPreguntas)
        txtRecordPreguntas = view.findViewById(R.id.recordPreguntas)
        btnInstagram = view.findViewById(R.id.btnInstagram)
        txtInstaUser = view.findViewById(R.id.txtInstaUser)
        txtUserName = view.findViewById(R.id.txtUserName)
        txtUserEmail = view.findViewById(R.id.txtUserEmail)

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture)


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

        btnPreguntas.setOnClickListener {
            val intent = Intent(requireContext(), GameActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun mostrarDialogoEditarPerfil() {
        val dialogView = Dialog(requireContext())
        dialogView.setContentView(R.layout.dialog_editar_perfil)

        // Encuentra las vistas en el diseño del diálogo
        editTextNuevoNombre = dialogView.findViewById(R.id.editTextNuevoNombre)
        editTextNuevoEmail = dialogView.findViewById(R.id.editTextNuevoEmail)
        btnGuardar = dialogView.findViewById(R.id.btnGuardar)
        btnCancel = dialogView.findViewById(R.id.btnCancel)
        btnBorrarDatos = dialogView.findViewById(R.id.btnBorrarDatos)



        editTextNuevoNombre.setText(txtUserName.text)
        editTextNuevoEmail.setText(txtUserEmail.text)

        btnBorrarDatos.setOnClickListener {
            editTextNuevoNombre.setText("")
            editTextNuevoEmail.setText("")
        }

        // Configura el diálogo
        btnGuardar.setOnClickListener{
                val nuevoNombre = editTextNuevoNombre.text.toString()
                val nuevoEmail = editTextNuevoEmail.text.toString()


                //actualizarDatosPerfil(nuevoNombre, , nuevoEmail, nuevoInsta)
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
        txtUserEmail.text = nuevoEmail
        txtInstaUser.text = nuevoInsta
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
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_PICK_IMAGE)
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