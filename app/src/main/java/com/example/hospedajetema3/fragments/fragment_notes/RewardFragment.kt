import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.hospedajetema3.R
import com.example.hospedajetema3.objects_models.Variables
import java.util.concurrent.CompletableFuture
import kotlin.random.Random


class RewardFragment : Fragment() {

    private lateinit var imageButton: ImageButton
    private lateinit var btnComprobar: Button
    private lateinit var handler: Handler
    private var isOnState = false // Comienza en off
    private var n = 1
    private var aux = 0
    private var x = 0
    private var y = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reward, container, false)

        btnComprobar = view.findViewById(R.id.btnComprobar)
        imageButton = view.findViewById(R.id.reclamarReward)
        handler = Handler()
        val contR = IntArray(20) { 0 }

        updateImageButton()

        handler.postDelayed(imageChanger, 2000)

        btnComprobar.setOnClickListener{
            if (contR.all { it > 0 }){
                resetearColeccion(view, contR)
                Variables.colectAll ++
            }else{
                Toast.makeText(context, "Todavia no has completado la coleccion", Toast.LENGTH_SHORT).show()
            }
        }

        imageButton.setOnClickListener {
            if (isOnState) {
                Variables.rewardDaily++
                // Cambia el estado de la recompensa de on a off
                isOnState = false
                updateImageButton()
                aux = Random.nextInt(100)+1
                n = convertirIndiceRamdon(aux)
                //n = 12 valor fijo
                //Toast.makeText(context, "RECOMPENSA RECLAMADA POS: $n", Toast.LENGTH_SHORT).show()
                contR[n-1] += 1

                val imagenId = "reward_$n"
                val resourceImageId = resources.getIdentifier(imagenId, "id", requireContext().packageName)
                val imageView = view.findViewById<ImageView>(resourceImageId)
                showCartaDialog(imageView){
                    imageView.visibility = View.VISIBLE


                    if (comprobarPorDiez(contR[n-1])){
                        contR[n-1] = 1
                        if (n <= 8){//Estamos en carta premium
                            val (numX,numY) = obtenerNumRandom(1,8)
                            x = numX
                            y = numY
                        }else if (n <= 12){//Estamos en carta Gold
                            val (numX,numY) = obtenerNumRandom(1,8)
                            x = numX
                            y = numY
                        }else if (n <= 20){//Estamos en carta Basic
                            val (numX,numY) = obtenerNumRandom(9,12)
                            x = numX
                            y = numY
                        }
                        val image1Id = "reward_$x"
                        val resourceImage1Id = resources.getIdentifier(image1Id, "id", requireContext().packageName)
                        val imagen1 = view.findViewById<ImageView>(resourceImage1Id)

                        val image2Id = "reward_$y"
                        val resourceImage2Id = resources.getIdentifier(image2Id, "id", requireContext().packageName)
                        val imagen2 = view.findViewById<ImageView>(resourceImage2Id)


                        showMejoraDialogo(context, imagen1, imagen2).thenApply {
                                opcion ->
                            if (opcion == 1){
                                contR[x-1] += 1
                                comprobarPorDiez(contR[x-1])
                                imagen1.visibility=View.VISIBLE
                                comprobarContador(view, contR, x)
                            }else if (opcion == 2){
                                contR[y-1] += 1
                                comprobarPorDiez(contR[y-1])
                                imagen2.visibility=View.VISIBLE
                                comprobarContador(view, contR, y)
                            }
                        }

                    }
                    comprobarContador(view, contR, n)
                }


            } else {
                handler.removeCallbacks(imageChanger)
                handler.postDelayed(imageChanger, 2000)
            }
        }



        return view
    }

    private fun convertirIndiceRamdon(x: Int): Int {
        //valor de x de 1 a 100
        when (x){
            in 1..7 -> {return 20}
            in 8..14 -> {return 19}
            in 15..21 -> {return 18}
            in 22..28 -> {return 17}
            in 29..35 -> {return 16}
            in 36..42 -> {return 15}
            in 43..49 -> {return 14}
            in 50..60 -> {return 13}
            //Probabilidades de carta Basic
            in 61..68 -> {return 12}
            in 69..76 -> {return 11}
            in 77..85 -> {return 10}
            in 86..92 -> {return 9}
            //Probabilidades de carta Gold
            93 -> {return 8}
            94 -> {return 7}
            95 -> {return 6}
            96 -> {return 5}
            97 -> {return 4}
            98 -> {return 3}
            99 -> {return 2}
            100 -> {return 1}
            //Probabilidades de carta Premium
        }
        return 20
    }

    private fun comprobarContador(view: View, contR: IntArray, num: Int) {
        val textId = "rewardCont_$num"
        val resourceTextId = resources.getIdentifier(textId, "id", requireContext().packageName)
        val textView = view.findViewById<TextView>(resourceTextId)
        textView.setText("x" + contR[num - 1].toString())
        textView.visibility = View.VISIBLE
    }
    private fun resetearColeccion(view: View,contR: IntArray) {
        for (i in 1 until 21) {
            contR[i] = 0
            val textId = "rewardCont_$i"
            val resourceTextId = resources.getIdentifier(textId, "id", requireContext().packageName)
            val textView = view.findViewById<TextView>(resourceTextId)
            textView.visibility = View.INVISIBLE

            val imagenId = "reward_$i"
            val resourceImageId = resources.getIdentifier(imagenId, "id", requireContext().packageName)
            val imageView = view.findViewById<ImageView>(resourceImageId)
            imageView.visibility = View.INVISIBLE
        }

    }

    fun comprobarPorDiez(n : Int) : Boolean {
        if (n>=5){
            return true
        }else{
            return false
        }
    }

    private fun obtenerNumRandom(min : Int, max : Int): Pair<Int, Int>{
        val num1 = Random.nextInt(min, max + 1)
        var num2 : Int

        do {
            num2 = Random.nextInt(min, max + 1)
        }while (num1 == num2)
        return num1 to num2
    }
    private fun showMejoraDialogo(
        context: Context?,
        imageView1: ImageView,
        imageView2: ImageView
    ): CompletableFuture<Int> {
        val completableFuture = CompletableFuture<Int>()

        val alertDialogBuilder = AlertDialog.Builder(context ?: return completableFuture)
        alertDialogBuilder.setTitle("ELIGE UNA OPCION: ")

        val mainLayout = LinearLayout(context)
        mainLayout.orientation = LinearLayout.VERTICAL
        mainLayout.gravity = Gravity.CENTER

        // Primer conjunto de RadioButton e ImageView
        val layout1 = LinearLayout(context)
        layout1.orientation = LinearLayout.HORIZONTAL
        layout1.gravity = Gravity.CENTER

        val radioGroup1 = RadioGroup(context)
        radioGroup1.orientation = RadioGroup.HORIZONTAL

        val radioButton1 = RadioButton(context)
        radioButton1.text = "Carta 1"
        radioGroup1.addView(radioButton1)

        val newImageView1 = ImageView(context)
        newImageView1.layoutParams = LinearLayout.LayoutParams(300, 400)
        newImageView1.setImageDrawable(imageView1.drawable)

        layout1.addView(radioGroup1)
        layout1.addView(newImageView1)

        // Segundo conjunto de RadioButton e ImageView
        val layout2 = LinearLayout(context)
        layout2.orientation = LinearLayout.HORIZONTAL
        layout2.gravity = Gravity.CENTER

        val radioGroup2 = RadioGroup(context)
        radioGroup2.orientation = RadioGroup.HORIZONTAL

        val radioButton2 = RadioButton(context)
        radioButton2.text = "Carta 2"
        radioGroup2.addView(radioButton2)

        val newImageView2 = ImageView(context)
        newImageView2.layoutParams = LinearLayout.LayoutParams(300, 400)
        newImageView2.setImageDrawable(imageView2.drawable)

        layout2.addView(radioGroup2)
        layout2.addView(newImageView2)

        mainLayout.addView(layout1)
        mainLayout.addView(layout2)

        alertDialogBuilder.setPositiveButton("CONFIRMAR") { _, _ ->
            val selectedOption = when {
                radioButton1.isChecked -> 1
                radioButton2.isChecked -> 2
                else -> 0
            }
            completableFuture.complete(selectedOption)
        }

        alertDialogBuilder.setOnDismissListener {
            // Manejar el caso cuando se cierra el diálogo sin confirmar
            completableFuture.complete(0)
        }

        alertDialogBuilder.setView(mainLayout)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        // Desmarcar el otro RadioButton cuando uno de ellos se seleccione
        radioButton1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                radioButton2.isChecked = false
            }
        }

        radioButton2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                radioButton1.isChecked = false
            }
        }

        return completableFuture
    }








    // Runnable para cambiar la imagen después de cierto tiempo
    private val imageChanger = object : Runnable {
        override fun run() {
            isOnState = true
            updateImageButton()

            handler.postDelayed(this, 2000) // Cambia la imagen cada 2 segundos
        }
    }

    // Método para actualizar la imagen del ImageButton según el estado
    private fun updateImageButton() {
        if (isOnState) {
            imageButton.setImageResource(R.drawable.recompensa_on)
        } else {
            imageButton.setImageResource(R.drawable.recompensa_off)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Asegúrate de detener el temporizador al destruir la vista para prevenir memory leaks
        handler.removeCallbacks(imageChanger)
    }

    private fun showCartaDialog(imageView: ImageView, afterDelay: () -> Unit) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("HAS OBTENIDO: ")
        val newImageView = ImageView(requireContext())
        newImageView.setImageDrawable(imageView.drawable)
        alertDialogBuilder.setView(newImageView)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        val durationInMillis = 2000L

        Handler(Looper.getMainLooper()).postDelayed({
            alertDialog.dismiss()
            afterDelay()
        }, durationInMillis)
    }
}
