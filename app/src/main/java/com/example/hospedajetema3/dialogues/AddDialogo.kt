import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.example.hospedajetema3.R // Importa el R de tu paquete
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hospedajetema3.models.Juego
import java.util.UUID
import kotlin.reflect.KFunction2


class AddDialogo(private val context: Context) {

    fun onCreateDialog(
        listJuegos: MutableList<Juego>,
        recyclerView: RecyclerView,
        addJuegoConfirm: KFunction2<Int, RecyclerView, Unit>
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.add_dialogo, null)

        // Obtén referencias a las vistas dentro de tu diseño personalizado
        val name = view.findViewById<EditText>(R.id.editTextName)
        val plat = view.findViewById<EditText>(R.id.editTextPlat)
        val anno = view.findViewById<EditText>(R.id.editTextAnno)
        val nota = view.findViewById<EditText>(R.id.editTextNota)
        val spinner = view.findViewById<Spinner>(R.id.spinnerImagen)

        val spinnerOption = arrayOf(
            "accion", "rpg", "multijugador", "miedo", "deportes",
            "estrategia", "aventura", "mundo_abierto", "misterio", "desconocido"
        )

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerOption)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        builder.setView(view)

            .setPositiveButton("Aceptar") { dialog, which ->

                val newJuego = arrayOfNulls<String>(5)

                newJuego[0] = name.text.toString()
                newJuego[1] = plat.text.toString()
                newJuego[2] = anno.text.toString()
                newJuego[3] = nota.text.toString()
                newJuego[4] = spinner.selectedItem.toString()
                if (newJuego[0] != "" && newJuego[1] != "" && newJuego[2] != "" && newJuego[3] != "" && newJuego[4] != "") {
                    val updatedJuego = Juego(generarId(), newJuego[0]!!, newJuego[1]!!, newJuego[2]!!, newJuego[3]!!,newJuego[4]!!)
                    listJuegos.add(updatedJuego)
                    val newPos = (listJuegos.size - 1)
                    addJuegoConfirm(newPos, recyclerView)
                } else {
                    Toast.makeText(
                        context,
                        "ERROR AL CREAR, DEBES RELLENAR TODOS LOS CAMPOS",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            .setNegativeButton("Cancelar") { dialog, which ->
                dialog?.cancel()
            }

        return builder.create()
    }

    private fun generarId(): String {
        return UUID.randomUUID().toString()
    }
}
