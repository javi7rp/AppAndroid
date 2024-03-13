package com.example.hospedajetema3.viewModel

import AddDialogo
import android.content.Context
import android.util.Log
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.hospedajetema3.adapter.AdapterJuego
import com.example.hospedajetema3.dialogues.DeleteDialogo
import com.example.hospedajetema3.dialogues.UpdateDialogo
import com.example.hospedajetema3.models.Juego
import com.example.hospedajetema3.models.Preferencias
import com.example.hospedajetema3.objects_models.Respository
import com.example.hospedajetema3.retrofit.RetrofitModule
import kotlinx.coroutines.launch

class JuegoViewModel() : ViewModel() {
    private val listJuego_ : MutableLiveData<List<Juego>> = MutableLiveData()
    private lateinit var preferencias : Preferencias

    fun init(context: Context) {
        preferencias = Preferencias(context)
        initData()
    }

    private fun initData(){
        viewModelScope.launch {
            try {
                val token = preferencias.obtenerTokenUsuario().toString()
                val response = RetrofitModule.apiService.getJuegos(token)

                if (response.isSuccessful && response.body()?.result == "ok") {
                    listJuego_.value = response.body()?.listJuegos ?: emptyList()

                } else {

                }
            } catch (e: Exception) {
                Log.e("JuegoViewModel", "Error", e)
            }
        }
    }

    fun getListJuegos() : LiveData<List<Juego>> {
        return listJuego_
    }

    private fun updateJuegoConfirm(pos : Int, recyclerView: RecyclerView){
        recyclerView.adapter?.notifyItemChanged(pos)
    }

    fun addJuegoConfirm(pos: Int, recyclerView: RecyclerView){
        recyclerView.adapter?.notifyItemInserted(pos)
        recyclerView.smoothScrollToPosition(pos)
    }

    fun setAdapter(recyclerView: RecyclerView){
        val adapter = AdapterJuego(
            listJuego_.value as MutableList<Juego>,
            { pos -> delJuego(pos, recyclerView) },
            { pos -> updateJuego(pos, recyclerView) }
        )
        recyclerView.adapter = adapter
    }

    fun setAddButton(addButton : ImageButton, recyclerView: RecyclerView){
        addButton.setOnClickListener {
            addJuego(recyclerView)
        }
    }

    fun addJuego(recyclerView: RecyclerView) {
        val dialog = AddDialogo(recyclerView.context)
        val alertdialog = dialog.onCreateDialog(listJuego_.value as MutableList<Juego>, recyclerView, ::addJuegoConfirm)
        alertdialog.show()
    }

    fun delJuego(pos: Int, recyclerView: RecyclerView) {
        val alertDialogHelper = DeleteDialogo(recyclerView.context)
        alertDialogHelper.deleteJuego(pos, listJuego_.value as MutableList<Juego>, listJuego_.value!![pos].nombre, recyclerView)
    }

    fun updateJuego(pos: Int, recyclerView: RecyclerView) {
        val dialog = UpdateDialogo(recyclerView.context)
        val alertDialog = dialog.showConfirmationDialog(pos, listJuego_.value as MutableList<Juego>, recyclerView, ::updateJuegoConfirm)
        alertDialog?.show()
    }



}