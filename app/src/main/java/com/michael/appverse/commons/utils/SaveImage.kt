package com.michael.appverse.commons.utils

import android.graphics.Bitmap
import android.os.Environment
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.util.*


object SaveImage {

    fun invoke(image: View?) {
        // TODO Auto-generated method stub
        //imageview to be used to save it.
        image?.setDrawingCacheEnabled(true)
        val bitmap: Bitmap? = image?.getDrawingCache()
        val root = Environment.getExternalStorageDirectory().toString()
        val newDir = File("$root/Android") //directory name of your choice
        newDir.mkdirs()
        val gen = Random()
        var n = 10000
        n = gen.nextInt(n)
        val fotoname = "Photo-$n.jpg"
        val file = File(newDir, fotoname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
        }
    }

}