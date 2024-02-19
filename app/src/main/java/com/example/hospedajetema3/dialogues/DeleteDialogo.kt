package com.example.hospedajetema3.dialogues

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hospedajetema3.R
import com.example.hospedajetema3.models.Juego

class DeleteDialogo(val context: Context)  {

    private val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

    fun deleteJuego(pos : Int,listaJuegos: MutableList<Juego>, name: String, recyclerView: RecyclerView){
        // Inflar el diseño personalizado del diálogo de eliminación
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.delete_dialogo, null)


        val titleTextView = view.findViewById<TextView>(R.id.text_delete_title)
        val messageTextView = view.findViewById<TextView>(R.id.text_delete_message)
        val gameNameTextView = view.findViewById<TextView>(R.id.text_game_name)


        gameNameTextView.text = name

        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        builder.setPositiveButton("Sí") { dialog, which ->
            Toast.makeText(context, "Borrado el $name", Toast.LENGTH_LONG).show()
            listaJuegos.removeAt(pos)
            recyclerView.adapter?.notifyItemRemoved(pos)
            recyclerView.adapter?.notifyItemRangeChanged(pos, listaJuegos.size)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }

        builder.show()

    }
}