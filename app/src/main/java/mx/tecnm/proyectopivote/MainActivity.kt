package mx.tecnm.proyectopivote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
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

        btnBuscar.setOnClickListener {
            listaBusqueda(EdTeidBuscar.text.toString())
        }

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
                var item ="idActividad: ${actividadesTmp.id}\n"+
                        "Descripcion: ${actividadesTmp.descripcion}\n" +
                        "Fecha entrega: ${actividadesTmp.fechaEntrega}\n" +
                        "Fecha captura: ${actividadesTmp.fechaCaptura}"

                listaID.add(actividadesTmp.id.toString())
                vector[it]= item

            }//forEach

            LiViActividades.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            LiViActividades.setOnItemClickListener { parent, view, position, id ->

                AlertDialog.Builder(this)
                    .setTitle("Acciones")
                    .setMessage("Que desea hacer con la actividad")
                    .setPositiveButton("Detalles"){d,i->
                        cargarDetalles(listaID[position])
                    }
                    .setNeutralButton("Eliminar"){d,i->

                    }
                    .show()

            }//LiViActividades


        }catch (e:Exception){
            dialogo("Atencion-Error", e.message.toString())
        }

    }//cargarLista

    fun cargarDetalles(idActividad:String){

        var ventana = Intent(this, MainActivity2::class.java)
        ventana.putExtra("idActividad", idActividad)
        startActivity(ventana)

    }

    fun listaBusqueda(idBuscar:String){

        try {

            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.buscar(idBuscar)

            var vector = Array<String>(1,{""})
            listaID.clear()

            if (conexion.error == 4){

                dialogo("Atencion","No se encontro el id")
                cargarLista()

            }else{

                var actividad = data
                var item = "idActivdad: ${actividad.id}\n" +
                        "Descripcion: ${actividad.descripcion}\n" +
                        "Fecha entrega_ ${actividad.fechaEntrega}\n" +
                        "Fecha captura: ${actividad.fechaCaptura}"

                vector[0] = item

                listaID.add(actividad.id.toString())
            }

            LiViActividades.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

        }catch (e:Exception){
            dialogo("Atencion", e.message.toString())
        }

    }//listaBusqueda

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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        var inflateDB = menuInflater
        inflateDB.inflate(R.menu.menuacciones, menu)

    }//onCreateContextMenu

    override fun onContextItemSelected(item: MenuItem): Boolean {

        try {

            if (idSeleccionado == -1){
                dialogo("Atencion", "No ha seleccionado un item")
            }//

        }catch (e:Exception){
            dialogo("Atencion", e.message.toString())
        }

        return true
    }//oncontextItem

}//class