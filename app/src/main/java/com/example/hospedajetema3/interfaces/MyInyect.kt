import android.content.Context
import android.widget.Toast
import javax.inject.Inject


interface MyInjectt {
    fun doSomething(context: Context)
}

class MyInjectImpl @Inject constructor() : MyInjectt {
    override fun doSomething(context: Context) {
        Toast.makeText(context, "BOTON PULSADO", Toast.LENGTH_SHORT).show()
    }
}



