package com.michael.appverse.commons.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object GenericDialogueBuilder {



     fun showDialogue(
         context: Context,
         style: Int,
         title: String?,
         message: String?,
         icon: Int,
         positiveButton: String?,
         negativeButton: String?,
         onPositiveClick: () -> Unit,
         onNegativeClick: () -> Unit
     ) {
        val dialog = MaterialAlertDialogBuilder(context, style)
            .setTitle(title)
            .setMessage(message)
            .setIcon(icon)
            .setPositiveButton(positiveButton) { _, _ ->
                onPositiveClick.invoke()
            }
            .setNegativeButton(negativeButton) { dialogInterface, _ ->
                dialogInterface.cancel()
                onNegativeClick.invoke()
            }
            .create()
        dialog.show()
    }
}