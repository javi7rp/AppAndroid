package com.example.hospedajetema3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospedajetema3.adapter.AdapterJuego
import com.example.hospedajetema3.databinding.FragmentInicioBinding

class FragmentFav : Fragment() {

    /*
    //Fondo de pantalla para pruebas
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }*/


/*
    private lateinit var controller: Controller
    private lateinit var binding: FragmentInicioBinding
    private lateinit var tvNoFavoritos: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        // Inicializa el Controller
        controller = Controller(requireContext(), binding)
        controller.setAdapter()

        // Usa el binding para obtener referencias a vistas
        val myRecyclerView = binding.myRecyclerView
        controller.setRecyclerView(myRecyclerView)

        //val addButton = binding.btnAdd
        //controller.setAddButton(addButton)

        tvNoFavoritos = binding.tvNoFavoritos
        initRecyclerView()
    }

    private fun initRecyclerView() {
        // Utiliza el binding para obtener referencias a vistas
        val myRecyclerView = binding.myRecyclerView
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val juegosFav = controller.obtenerJuegosFav().toMutableList()
        val adapterFav = AdapterJuego(
            juegosFav,
            { gameId -> controller.deleteJuego(gameId) },
            { gameId -> controller.updateJuego(gameId) },
            { gameId -> controller.toggleFav(gameId) },
        )

        myRecyclerView.adapter = adapterFav
        tvNoFavoritos.visibility = if (juegosFav.isEmpty()) View.VISIBLE else View.GONE
    }

 */





}