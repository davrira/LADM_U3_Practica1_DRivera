package mx.tecnm.proyectopivote

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Actividades (descripcionP:String, fechaCapturaP:String, fechaEntregaP:String) {

    var descripcion = descripcionP
    var fechaCaptura = fechaCapturaP
    var fechaEntrega = fechaEntregaP
    var id = 0
    var error = -1

    val nombreBaseDatos = "Evidencias"
    var puntero : Context ?= null

    fun asignarPuntero(p:Context){
        puntero = p
    }//asignarPuntero

    /*
    Errores
    ------
    1->error al crear base o tabla
    2->Error al insertar
    3->Error al cargar todos
     */

    fun insertar(): Boolean{

        error = -1

        try {

            var base = BaseDatos(puntero, nombreBaseDatos, null, 1)
            var transaccion = base.writableDatabase
            var datos = ContentValues()

            datos.put("Descripcion", descripcion)
            datos.put("FechaCaptura", fechaCaptura)
            datos.put("FechaEntrega", fechaEntrega)

            var respuesta = transaccion.insert("Actividad","idActividad",datos)

            if (respuesta == -1L){

                error = 2
                return false

            }//if

            transaccion.close()

        }catch (e:SQLiteException){

            error = 1
            return false

        }//try-catch

        return true

    }//insertar

    fun mostrarTodos() : ArrayList<Actividades> {

        var datos = ArrayList<Actividades>()
        error = -1

        try {

            var base = BaseDatos(puntero, nombreBaseDatos, null, 1)
            var transacion = base.readableDatabase
            var columnas = arrayOf("*")

            var respuesta = transacion.query("Actividad", columnas, null, null, null, null, null)

            if (respuesta.moveToFirst()){

                do {

                    var actividadesTmp = Actividades(respuesta.getString(1), respuesta.getString(2), respuesta.getString(3))
                    actividadesTmp.id = respuesta.getInt(0)
                    datos.add(actividadesTmp)

                }while (respuesta.moveToNext())

            }else{
                error = 3
            }

            transacion.close()

        }catch (e:SQLiteException){
            error = 1
        }//try-catch

        return datos

    }//mostrarTodos

}//class