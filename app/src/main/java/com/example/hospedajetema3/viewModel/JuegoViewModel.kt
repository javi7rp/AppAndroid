package com.example.hospedajetema3.viewModel

import AddDialogo
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.hospedajetema3.adapter.AdapterJuego
import com.example.hospedajetema3.dialogues.DeleteDialogo
import com.example.hospedajetema3.dialogues.UpdateDialogo
import com.example.hospedajetema3.models.Juego
import com.example.hospedajetema3.objects_models.Respository

class JuegoViewModel() : ViewModel() {
    private val listJuego_ : MutableLiveData<List<Juego>> = MutableLiveData()

    init {
        initData()
    }

    private fun initData(){
        listJuego_.value = Respository.listJuego.toMutableList()
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
        alertDialogHelper.deleteJuego(pos, listJuego_.value as MutableList<Juego>, listJuego_.value!![pos].name, recyclerView)
    }

    fun updateJuego(pos: Int, recyclerView: RecyclerView) {
        val dialog = UpdateDialogo(recyclerView.context)
        val alertDialog = dialog.showConfirmationDialog(pos, listJuego_.value as MutableList<Juego>, recyclerView, ::updateJuegoConfirm)
        alertDialog?.show()
    }



}