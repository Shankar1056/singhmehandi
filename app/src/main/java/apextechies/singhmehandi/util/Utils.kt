package apextechies.singhmehandi.util

import android.app.AlertDialog
import android.content.Context
import apextechies.singhmehandi.R
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    fun showAlertDialog(context: Context, msg: String, okBtn: String, noBtn: String, cancelBtn: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.resources.getString(R.string.app_name))
        builder.setMessage(msg)
        builder.setPositiveButton(okBtn) { dialog, which ->
            // Do something when user press the positive button
        }
        // Display a negative button on alert dialog
        builder.setNegativeButton(noBtn) { dialog, which ->
        }
        // Display a neutral button on alert dialog
        builder.setNeutralButton(cancelBtn) { _, _ ->
        }
        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()
        // Display the alert dialog on app interface
        dialog.show()
    }

    fun getCurrentDate(): String{
        val c = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("MM-dd-yyyy")
        val formattedDate = df.format(c)
        return formattedDate
    }
    fun getCurrentDateForOrder(): String{
        val c = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = df.format(c)
        return formattedDate
    }
    fun getCurrentDateWithDash(): String{
        val c = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("MM/dd/yyyy")
        val formattedDate = df.format(c)
        return formattedDate
    }
}
