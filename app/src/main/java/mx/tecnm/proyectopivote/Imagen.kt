package mx.tecnm.proyectopivote

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

object DbBitmapUtility{

    //bitmap -> byteArrya
    fun getBytes(bitmap : Bitmap ) : ByteArray{

        var stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()

    }//getBytes


    //byteArray -> image
    fun getImage(image : ByteArray) : Bitmap{
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }//getImage

}//object
