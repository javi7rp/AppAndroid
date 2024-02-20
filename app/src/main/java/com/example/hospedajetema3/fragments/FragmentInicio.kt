package com.example.hospedajetema3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospedajetema3.R
import com.example.hospedajetema3.databinding.FragmentInicioBinding
import com.example.hospedajetema3.viewModel.JuegoViewModel

class FragmentInicio : Fragment() {

    private val juegoViewModel : JuegoViewModel by viewModels()
    private lateinit var myRecyclerView: RecyclerView
    lateinit var binding: FragmentInicioBinding
    lateinit var  botonFlotante : ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        val rootView = binding.root
        botonFlotante = rootView.findViewById(R.id.btn_add)
        myRecyclerView = binding.myRecyclerView
        init()
        return binding.root
    }

    private fun init() {
        initRecyclerView()

        juegoViewModel.getListJuegos().observe(viewLifecycleOwner, {juego ->})
        juegoViewModel.setAdapter(myRecyclerView)
        juegoViewModel.setAddButton(botonFlotante, myRecyclerView)


    }

    private fun initRecyclerView() {
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


}
