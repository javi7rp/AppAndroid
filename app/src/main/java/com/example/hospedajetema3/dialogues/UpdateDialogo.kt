package com.example.hospedajetema3.dialogues

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hospedajetema3.R
import com.example.hospedajetema3.models.Juego

class UpdateDialogo(private val context: Context) {

    fun showConfirmationDialog(
        pos: Int,
        listJuegos: MutableList<Juego>,
        recyclerView: RecyclerView,
        updateJuegoConfirm: (Int, RecyclerView) -> Unit
    ): AlertDialog? {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.update_dialogo, null)

        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextName)
        val platEditText = dialogView.findViewById<EditText>(R.id.editTextPlat)
        val annoEditText = dialogView.findViewById<EditText>(R.id.editTextAnno)
        val notaEditText = dialogView.findViewById<EditText>(R.id.editTextNota)

        // Set the initial values from the listJuegos to the EditText fields
        val selectedJuego = listJuegos[pos]
        nameEditText.setText(selectedJuego.name)
        platEditText.setText(selectedJuego.plataforma)
        annoEditText.setText(selectedJuego.anno)
        notaEditText.setText(selectedJuego.nota)

        builder.setView(dialogView)
            .setPositiveButton("Aceptar") { dialog, which ->
                val newName = nameEditText.text.toString()
                val newPlat = platEditText.text.toString()
                val newAnno = annoEditText.text.toString()
                val newNota = notaEditText.text.toString()

                if (newName.isNotBlank() && newPlat.isNotBlank() && newAnno.isNotBlank() && newNota.isNotBlank()) {
                    listJuegos[pos] = Juego(selectedJuego.id, newName, newPlat, newAnno, newNota, selectedJuego.image)
                    updateJuegoConfirm(pos, recyclerView)
                } else {
                    // Show an error message if any field is empty
                    Toast.makeText(
                        context,
                        "ERROR AL MODIFICAR, DEBES RELLENAR TODOS LOS CAMPOS",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.cancel()
            }

        return builder.create()
    }
}
