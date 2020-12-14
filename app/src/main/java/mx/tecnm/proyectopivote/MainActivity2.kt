package mx.tecnm.proyectopivote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    var idActividad = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var extra = intent.extras
        idActividad = extra!!.getString("idActividad")!!

        vtnAgregarTeViIdActivdad.setText("La actividad es: ${idActividad}")

    }//onCreate

}//class