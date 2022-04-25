package com.michael.appverse.features.locationTracker.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.michael.appverse.commons.ui.log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


object ImageManager {
    private fun storeImage(image: Bitmap) {
        val pictureFile: File? = getOutputMediaFile()

        if (pictureFile == null) {
            log("Error creating media file, check storage permissions: "
            ) // e.getMessage());
            return
        }
        try {
            val fos = FileOutputStream(pictureFile)
            image.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.close()

//            val bmpUri: Uri = Uri.parse(pictureFile)
//            val emailIntent1 = Intent(Intent.ACTION_SEND)
//            emailIntent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            emailIntent1.putExtra(Intent.EXTRA_STREAM, bmpUri)
//            emailIntent1.type = "image/png"
        } catch (e: FileNotFoundException) {
            log("File not found: " + e.message)
        } catch (e: IOException) {
            log("Error accessing file: " + e.message)
        }
    }

    private fun getOutputMediaFile(): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        val mediaStorageDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/Android/data/"
                    + getApplicationContext<Context>().packageName
                    + "/Files"
        )

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        // Create a media file name
        val timeStamp: String = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
        val mediaFile: File
        val mImageName = "MI_$timeStamp.jpg"
        mediaFile = File(mediaStorageDir.path + File.separator.toString() + mImageName)
        return mediaFile
    }
}