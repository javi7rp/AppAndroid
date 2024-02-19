package com.example.hospedajetema3.fragments.fragment_notes

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.hospedajetema3.R
import com.example.hospedajetema3.interfaces.Observador
import com.example.hospedajetema3.objects_models.Variables

class InsigniasFragment : Fragment(), Observador{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insignias, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val insigniaButton1 = view.findViewById<ImageButton>(R.id.insignia1)
        val insigniaButton2 = view.findViewById<ImageButton>(R.id.insignia2)
        val insigniaButton3 = view.findViewById<ImageButton>(R.id.insignia3)

        (requireActivity() as? Observador)?.let { observador ->
            observador.onValueChanged(Variables.colectAll)
            observador.onValueChanged(Variables.allPreguntas)
            observador.onValueChanged(Variables.rewardDaily)
        }


        if (Variables.rewardDaily > 5){
            insigniaButton1.setImageResource(R.drawable.instagram)
        }

        insigniaButton1.setOnClickListener { showInsigniaDialog("COLECCION", "HAS CONSEGUIDO TODA LA COLECCION UN TOTAL DE ${Variables.colectAll} VECES") }
        insigniaButton2.setOnClickListener { showInsigniaDialog("PREGUNTAS", "HAS RESPONDIDO TODAS LAS PREGUNTAS BIEN UN TOTAL DE ${Variables.allPreguntas} VECES") }
        insigniaButton3.setOnClickListener { showInsigniaDialog("REWARD DIARIAS", "HAS OBTENIDO UN TOTAL DE ${Variables.rewardDaily} RECOMPENSAS") }
    }

    private fun showInsigniaDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, id -> dialog.dismiss() }
        builder.create().show()
    }

    override fun onValueChanged(value: Int) {
        //val insigniaButton1 = view?.findViewById<ImageButton>(R.id.insignia1)
        //val insigniaButton2 = view?.findViewById<ImageButton>(R.id.insignia2)
        val insigniaButton3 = view?.findViewById<ImageButton>(R.id.insignia3)

        when {
            value == 5 -> insigniaButton3?.setImageResource(R.drawable.instagram)
            value == 10 -> insigniaButton3?.setImageResource(R.drawable.casa)
            // Puedes agregar más casos según sea necesario
        }
    }
}