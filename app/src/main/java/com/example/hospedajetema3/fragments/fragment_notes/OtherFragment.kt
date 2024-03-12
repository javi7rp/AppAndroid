package com.example.hospedajetema3.fragments.fragment_notes


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.hospedajetema3.R
//import com.example.hospedajetema3.interfaces.MyInject

//inyeccion

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class OtherFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_other, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btnInject = view.findViewById<Button>(R.id.btnInject)

        btnInject.setOnClickListener {
           // myInjectt.doSomething(requireContext())
            Toast.makeText(context, "BOTON PULSADO", Toast.LENGTH_SHORT).show()
        }
    }
}
