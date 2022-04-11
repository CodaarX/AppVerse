package com.decagon.n26_p3_usecase.commons.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewController.TrackLocationActivity
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


@SuppressLint("LogNotTimber")
fun <T> log(value : T){
    Log.i("INFORMATION", "log: ${value}")
}


fun toast(context: Context, message : String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showSnackBar(view: CardView, message: String){
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val view1 = snackbar.view
    val textView = view1.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(Color.RED)
    snackbar.show()
}

inline fun <reified T> snack(view: T, message: String){
    when(view){
       is View -> Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
        is TrackLocationActivity -> Snackbar.make(view.findViewById(R.id.location_activity_root), message, Snackbar.LENGTH_LONG).show()
    }
}

fun View.hideView(){
    visibility = View.GONE
}

fun View.showView(){
    visibility = View.VISIBLE
}

fun <T> timber(message: T){
    Timber.d("TIMBER: $message")
}


/*Extension function to observe Live Data only once*/
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(
        lifecycleOwner,
        object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        }
    )
}

fun Fragment.navigateTo(id: Int) {
    findNavController().navigate(id)
}

fun Fragment.navigateTo(direction: NavDirections) {
    findNavController().navigate(direction)
}



fun Fragment.uriToBitmap(uriImage: Uri): Bitmap? {

    val contentResolver = requireActivity().contentResolver
    var mBitmap: Bitmap? = null
    mBitmap = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(
            contentResolver,
            uriImage
        )
    } else {
        val source = ImageDecoder.createSource(contentResolver, uriImage)
        ImageDecoder.decodeBitmap(source)
    }
    return mBitmap
}

fun Fragment.saveBitmap(bmp: Bitmap?): File? {
    val extStorageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    var outStream: OutputStream? = null
    var file: File? = null
    val time = System.currentTimeMillis()
    val child = "JPEG_${time}_.jpg"

    // String temp = null;
    if (extStorageDirectory != null) {
        file = File(extStorageDirectory, child)
        if (file.exists()) {
            file.delete()
            file = File(extStorageDirectory, child)
        }
        try {
            outStream = FileOutputStream(file)
            bmp?.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    return file
}
