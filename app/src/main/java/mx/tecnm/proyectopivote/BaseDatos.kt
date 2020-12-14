package mx.tecnm.proyectopivote

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE Actividad(" +
                    "idActividad INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Descripcion VARCHAR (2000)," +
                    "FechaCaptura DATE," +
                    "FechaEntrega DATE"+
                    ");"
        )//dbExec--tabla->actividades

    }//onCreate

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }//onUpgrade

}//class