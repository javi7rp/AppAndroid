package com.example.hospedajetema3


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hospedajetema3.fragments.FragmentPerfil
import com.example.hospedajetema3.models.Pregunta
import com.example.hospedajetema3.objects_models.Variables

class GameActivity : AppCompatActivity(){
    private lateinit var questionTextView: TextView
    private lateinit var contQuestionTextView: TextView
    private lateinit var contAciertosTextView: TextView
    private lateinit var answerOptionsGroup: RadioGroup
    private lateinit var submitAnswerButton: Button

    private var questionCounter = 0
    private var contAciertos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)

        questionTextView = findViewById(R.id.questionTextView)
        contQuestionTextView = findViewById(R.id.contQuestionTextView)
        contAciertosTextView = findViewById(R.id.contAciertosTextView)
        answerOptionsGroup = findViewById(R.id.answerOptionsGroup)
        submitAnswerButton = findViewById(R.id.submitAnswerButton)


        showNextQuestion()

    }
    private val listaDePreguntas = listOf(
        Pregunta(
            "¿Cuál NO es una carta del Clash Royale?",
            listOf("Bolero", "Montapuercos", "Bruja Arquera", "Príncipe Oscuro"),
            "Bruja Arquera"
        ),
        /*
        Pregunta(
            "¿Cuál de los siguientes elementos químicos es un gas noble?",
            listOf("Hierro", "Helio", "Sodio", "Oxígeno"),
            "Helio"
        ),
        Pregunta(
            "¿En qué año se firmó la Declaración de Independencia de los Estados Unidos?",
            listOf("1776", "1789", "1804", "1812"),
            "1776"
        ),
        Pregunta(
            "¿Cuál es el río más largo del mundo?",
            listOf("Amazonas", "Nilo", "Yangtsé", "Misisipi"),
            "Nilo"
        ),
        Pregunta(
            "¿Quién pintó la Mona Lisa?",
            listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"),
            "Leonardo da Vinci"
        ),
        Pregunta(
            "¿Cuál es la obra más famosa de William Shakespeare?",
            listOf("Hamlet", "Don Quijote", "Romeo y Julieta", "Orgullo y prejuicio"),
            "Romeo y Julieta"
        ),
        Pregunta(
            "¿Quién es conocido como el 'Rey del Pop'?",
            listOf("Elvis Presley", "Michael Jackson", "Madonna", "The Beatles"),
            "Michael Jackson"
        ),
        Pregunta(
            "¿En qué deporte se utiliza una pala para golpear una pelota sobre una red?",
            listOf("Tenis", "Golf", "Ping Pong", "Bádminton"),
            "Ping Pong"
        ),
        Pregunta(
            "¿Cuál es la capital de Australia?",
            listOf("Sídney", "Melbourne", "Canberra", "Brisbane"),
            "Canberra"
        ),
        Pregunta(
            "¿Cuál de las siguientes compañías fue fundada por Steve Jobs, Steve Wozniak y Ronald Wayne?",
            listOf("Microsoft", "Google", "Apple", "IBM"),
            "Apple"
        )

         */

    )

    private fun showQuestion(questionIndex: Int, question: String, options: List<String>, correctAnswer: String) {
        // Muestra la pregunta
        questionTextView.text = question
        contQuestionTextView.setText("Pregunta " + (questionIndex + 1))

        // Agrega opciones de respuesta dinámicamente
        answerOptionsGroup.removeAllViews()
        for (option in options) {
            val radioButton = RadioButton(this)
            radioButton.text = option
            answerOptionsGroup.addView(radioButton)
        }

        // Configura el botón de respuesta
        submitAnswerButton.setOnClickListener {
            val selectedOptionId = answerOptionsGroup.checkedRadioButtonId
            if (selectedOptionId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedOptionId)
                val selectedAnswer = selectedRadioButton.text.toString()

                // Lógica para verificar la respuesta y mostrar la siguiente pregunta
                handleAnswer(selectedAnswer, correctAnswer)
            }
        }
    }
    private fun handleAnswer(selectedAnswer: String, correctAnswer: String) {
        questionCounter++
        if (selectedAnswer == correctAnswer) {
            contAciertos ++
            showAnswerDialog(true, correctAnswer)
            contAciertosTextView.setText("Aciertos: " + contAciertos + "/" + questionCounter)
        } else {
            showAnswerDialog(false, correctAnswer)
            contAciertosTextView.setText("Aciertos: " + (contAciertos) + "/" + questionCounter)

        }




    }
    private fun showNextQuestion() {
        val nextQuestion = listaDePreguntas[questionCounter]
        showQuestion(
            questionCounter,
            nextQuestion.pregunta,
            nextQuestion.opciones,
            nextQuestion.respuestaCorrecta
        )
    }
    private fun showAnswerDialog(acierto: Boolean, correctAnswer: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("SOLUCIÓN")
        if (acierto){
            builder.setMessage("RESPUESTA CORRECTA!!")
        }else{
            builder.setMessage("RESPUESTA INCORRECTA\nLa solución era: " + correctAnswer)
        }

        builder.setPositiveButton("ACEPTAR") { dialog, _ -> dialog.dismiss()
            //Muestra la siguiente pregunta (puedes implementar esta lógica según tu estructura de juego)
            if (questionCounter < listaDePreguntas.size){
                showNextQuestion()
            }else{
                showFinalDialog(contAciertos)

            }

        }
        builder.show()
    }
    private fun showFinalDialog(contAciertos: Int) {

        if (contAciertos == listaDePreguntas.size){
            Variables.allPreguntas ++
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("FIN DEL JUEGO")
        builder.setMessage("HAS ACERTADO: " + contAciertos + "/" + questionCounter)
        builder.setPositiveButton("TERMINAR") { dialog, _ ->
            dialog.dismiss()
            finish()
            val fragmentPerfil = supportFragmentManager.findFragmentById(R.id.fragment_container) as? FragmentPerfil
            fragmentPerfil?.let {
                if (!it.isResultadoActualizado) {
                    it.actualizarResultado(contAciertos)
                    Log.d("IIIIIII", "Encuentra el fragmento")
                }else {
                    Log.d("IIIIIII", "Fragmento encontrado, pero el resultado ya está actualizado")
                }
            } ?: Log.d("IIIIIII", "Fragmento no encontrado")

        }
        builder.show()
    }


}