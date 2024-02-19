package com.example.hospedajetema3.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hospedajetema3.models.Juego
import com.example.hospedajetema3.databinding.RecyclerViewBinding
import com.bumptech.glide.Glide
import com.example.hospedajetema3.R
import com.example.hospedajetema3.fragments.FragmentFav

class ViewJuego (
                view: View,
                 var deleteOnClick: (Int) -> Unit,
                 var updateOnClick: (Int) -> Unit
                 //var addFav: (String) -> Unit
    ) : RecyclerView.ViewHolder (view){
        //var btn_fav: ImageButton
    private var binding: RecyclerViewBinding
    private val context: Context = itemView.context
    init {
        binding = RecyclerViewBinding.bind(view)
        //btn_fav = binding.btnFav
    }
    fun renderize(juego: Juego, context: Context){
        binding.txtviewName.setText(juego.name)
        binding.txtviewPlataforma.setText(juego.plataforma)
        binding.txtviewAnno.setText(juego.anno)
        binding.txtviewNota.setText(juego.nota)
        val resourceId = context.resources.getIdentifier(
            juego.image, "drawable", context.packageName
        )
        binding.ivJuego.setImageResource(resourceId)

    }

    /*
    loadImageWithGlide(binding.ivJuego, juego.image)
    fun loadImageWithGlide(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
*/
   fun setOnClickListener(pos : Int){
        binding.btnEdit.setOnClickListener {
            Log.i("aaaa" , "edit juego")
            updateOnClick(pos)
        }
        binding.btnDelete.setOnClickListener {
            Log.i("aaaa" , "delete juego")
            deleteOnClick(pos)
        }
        /*binding.btnFav.setOnClickListener(){
            Log.i("aaaa" , "pulsado en fav")
            addFav(gameId)

            /*
            if (context is AppCompatActivity){
                val currentFragment = context.supportFragmentManager.findFragmentById(R.id.fragment_container)

                if (currentFragment is FragmentFav){
                    val context = itemView.context
                    val fragmentFav = FragmentFav()
                    val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, fragmentFav)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }

             */
        }

         */

    }
}
