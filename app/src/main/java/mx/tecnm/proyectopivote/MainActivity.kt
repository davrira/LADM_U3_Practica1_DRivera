package mx.tecnm.proyectopivote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var idSeleccionado = -1
    var listaID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cargarLista()

        btnInsertar.setOnClickListener {
            insertar()
        }//insertar

    }//onCreate


    fun insertar(){

        var descripcionTmp = EdTeDescripcion.text.toString()
        var fechaEntregaTmp = EdTeFechaEntrega.text.toString()
        var fechaCapturaTmp = EdTeFechaCaptura.text.toString()

        var actividadTmp = Actividades(descripcionTmp, fechaCapturaTmp, fechaEntregaTmp)

        actividadTmp.asignarPuntero(this)

        var resultado = actividadTmp.insertar()

        if (resultado){

            mensaje("Insercion exitosa")
            limpiarCampos()

        }else{
            when(actividadTmp.error){
                1->{dialogo("Atencion", "Error con la tabla (No se creo o no se conecto)")}
                2->{dialogo("Atencion", "Error al insertar")}
            }
        }

        cargarLista()

    }//insertar

    fun cargarLista(){

        try {

            var conexion = Actividades("","","")
            conexion.asignarPuntero(this)

            var data = conexion.mostrarTodos()
            if (data.size == 0){
                if (conexion.error==3){
                    dialogo("Atencion","Sin datos en la tabla")
                }
                return
            }//

            var total = data.size-1
            var vector = Array<String>(data.size, {""})
            listaID.clear()

            (0..total).forEach{

                var actividadesTmp = data[it]
                var item = "Descripcion: ${actividadesTmp.descripcion}\n" +
                        "Fecha entrega: ${actividadesTmp.fechaEntrega}\n" +
                        "Fecha captura: ${actividadesTmp.fechaCaptura}"

                listaID.add(actividadesTmp.id.toString())
                vector[it]= item

            }//forEach

            LiViActividades.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

        }catch (e:Exception){
            dialogo("Atencion-Error", e.message.toString())
        }

    }//cargarLista

    fun dialogo(tituloP:String, mensajeP:String){

        AlertDialog.Builder(this)
            .setTitle(tituloP)
            .setMessage(mensajeP)
            .setPositiveButton("Ok"){d, i->}
            .show()

    }//dialogo

    fun mensaje(mensaje:String){

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()

    }//mensaje

    fun limpiarCampos(){

        EdTeDescripcion.setText("")
        EdTeFechaEntrega.setText("")
        EdTeFechaCaptura.setText("")

    }//limpiarCampos

}//class