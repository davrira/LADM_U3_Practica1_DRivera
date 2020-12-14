package mx.tecnm.proyectopivote

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Evidencia (idActividadP : Int, imagenP:ByteArray){

    var idActividad = idActividadP
    var imagen = imagenP
    var id = 0
    var error = -1

    val nombreBaseDatos = "Evidencias"
    var puntero : Context?= null

    /*
    1->error al crear o conectar con base o tabla
    2->error al insertar
    3->tabla vacia o error al leer
     */

    fun insertar() : Boolean{

        error = -1

        try {

            var base = BaseDatos(puntero, nombreBaseDatos, null, 1)
            var transaccion = base.writableDatabase
            var datos = ContentValues()

            datos.put("idActividad", idActividad)
            datos.put("Foto", imagen)

            var respuesta = transaccion.insert("Evidencias", "idEvidencia", datos)

            if (respuesta == -1L){

                error = 2
                return false

            }

            transaccion.close()

        }catch (e:SQLiteException){
            error = 1
        }//try-catch

        return true

    }//insertar

    fun mostrarTodo() : ArrayList<Evidencia>{

        var data = ArrayList<Evidencia>()
        error = -1

        try {

            var base = BaseDatos(puntero, nombreBaseDatos, null, 1)
            var transaccion = base.readableDatabase
            var columnas = arrayOf("*")

            var respuesta = transaccion.query("Evidencias", columnas, null, null, null, null, null)

            if (respuesta.moveToFirst()){

                do {

                    var evidenciaTmp = Evidencia(respuesta.getInt(1), respuesta.getBlob(2))
                    evidenciaTmp.id = respuesta.getInt(0)
                    data.add(evidenciaTmp)

                }while (respuesta.moveToNext())

            }else{
                error = 3
            }

            transaccion.close()

        }catch (e:SQLiteException){
            error=1
        }

        return  data

    }//mostrarTodos

}//Evidencia