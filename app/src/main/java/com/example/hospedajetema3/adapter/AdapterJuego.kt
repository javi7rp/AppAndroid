package com.example.hospedajetema3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hospedajetema3.R
import com.example.hospedajetema3.fragments.FragmentFav
import com.example.hospedajetema3.models.Juego

class AdapterJuego(
    var listJuego : MutableList<Juego>,
    var deleteOnClick: (Int) -> Unit,
    var updateOnClick: (Int) -> Unit,
    //var addFav: (String) -> Unit
    ) : RecyclerView.Adapter<ViewJuego>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewJuego {
        val layoutInflater = LayoutInflater.from(parent. context)
        val layoutItemJuego = R.layout.recycler_view
        return ViewJuego(
            layoutInflater.inflate(layoutItemJuego, parent, false),
            deleteOnClick,
            updateOnClick
            //addFav
        )
    }

    override fun onBindViewHolder(holder: ViewJuego, position: Int) {
        val juego = listJuego[position]
        holder.renderize(juego, holder.itemView.context)

        /*
        if (juego.isFav){
            holder.btn_fav.setImageResource(R.drawable.fav)
        }else{
            holder.btn_fav.setImageResource(R.drawable.no_fav)
        }

         */

        holder.setOnClickListener(position)
    }
    override fun getItemCount(): Int = listJuego.size

}