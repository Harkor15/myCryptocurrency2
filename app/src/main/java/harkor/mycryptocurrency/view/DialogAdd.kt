package harkor.mycryptocurrency.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import harkor.mycryptocurrency.R
import kotlinx.android.synthetic.main.add_dialog.view.*


class DialogAdd(private val noticeAddDialogListener: NoticeAddDialogListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.add_dialog, null)
            dialogView.add_cancel.setOnClickListener {
                dialog.cancel()
            }
            dialogView.add_ok.setOnClickListener {
                val name = dialogView.add_edit_name.text.toString()
                val amount = dialogView.add_edit_amount.text.toString().toDoubleOrNull()
                if (amount != null && name != "") {
                    noticeAddDialogListener.addNewCrypto(name, amount)
                    dialog.cancel()
                }
            }
            builder.setView(dialogView)
            builder.create()
        } ?: throw IllegalAccessException("Activity cannot be null")
    }
}

interface NoticeAddDialogListener {
    fun addNewCrypto(name: String, amount: Double)
}