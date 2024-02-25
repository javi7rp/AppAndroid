import android.content.Context
import android.widget.Toast
//import javax.inject.Inject

interface MyInject {
    fun doSomething(context: Context)
}
/*
class MyInjectImpl @Inject constructor() : MyInject {
    override fun doSomething(context: Context) {
        Toast.makeText(context, "USUARIO NO REGISTRADO o CAMPOS NO RELLENADOS", Toast.LENGTH_SHORT).show()
    }
}


 */