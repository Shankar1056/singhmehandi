package apextechies.singhmehandi.util

import android.app.AlertDialog
import android.content.Context
import apextechies.singhmehandi.R
import apextechies.singhmehandi.component.activity.order.model.OrderDescriptionQuantityModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object Utils {

    fun showAlertDialog(
        context: Context,
        msg: String,
        okBtn: String,
        noBtn: String,
        cancelBtn: String
    ) {
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

    fun getCurrentDate(): String {
        val c = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("MM-dd-yyyy")
        val formattedDate = df.format(c)
        return formattedDate
    }

    fun getCurrentDateWithhifun(): String {
        val c = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = df.format(c)
        return formattedDate
    }

    fun getCurrentDateWithDash(): String {
        val c = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("MM/dd/yyyy")
        val formattedDate = df.format(c)
        return formattedDate
    }

    fun getDescriptionName(descQuanList: ArrayList<OrderDescriptionQuantityModel>): ArrayList<String> {
        var list = ArrayList<String>()

        for (i in 0 until descQuanList.size) {
            list.add(descQuanList[i].descriptionName!!)
        }
        return list
    }

    fun getDescriptionId(descQuanList: ArrayList<OrderDescriptionQuantityModel>): ArrayList<String> {
        var list = ArrayList<String>()

        for (i in 0 until descQuanList.size) {
            list.add(descQuanList[i].desc_id!!)
        }
        return list
    }
    fun getQuantity(descQuanList: ArrayList<OrderDescriptionQuantityModel>): ArrayList<String> {
        var list = ArrayList<String>()

        for (i in 0 until descQuanList.size) {
            list.add(descQuanList[i].Quantity!!)
        }
        return list
    }
}
