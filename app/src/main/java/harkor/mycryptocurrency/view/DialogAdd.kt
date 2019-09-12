package harkor.mycryptocurrency.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import harkor.mycryptocurrency.R
import kotlinx.android.synthetic.main.add_dialog.view.*


class DialogAdd(private val noticeAddDialogListener: NoticeAddDialogListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            //val inflater = requireActivity().layoutInflater
            val dialogView=LayoutInflater.from(activity).inflate(R.layout.add_dialog,null)

            dialogView.add_cancel.setOnClickListener {
                dialog.cancel()
            }
            dialogView.add_ok.setOnClickListener {
                val name=dialogView.add_edit_name.text.toString()
                val amount=dialogView.add_edit_amount.text.toString().toDoubleOrNull()
                if(amount!=null&&name!=""){
                    Log.d("MyCrypto", "Name: $name amount: $amount")
                    noticeAddDialogListener.addNewCrypto(name,amount)
                    dialog.cancel()
                }else{
                    Log.d("MyCrypto", "double null!")
                }

            }
            builder.setView(dialogView)
            builder.create()
        } ?: throw IllegalAccessException("Activity cannot be null")

    }


/*
    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val addDialogViewmodel = AddDialogViewmodel(this)
        val builder = AlertDialog.Builder(activity)
        val mView = activity.layoutInflater.inflate(R.layout.add_dialog, null)
        context = mView.context
        builder.setView(mView)
                .setNegativeButton(R.string.cancel) { dialogInterface, i -> }
                .setPositiveButton(R.string.ok) { dialogInterface, i ->
                    val editName = mView.findViewById<EditText>(R.id.edit_name)
                    val editAmount = mView.findViewById<EditText>(R.id.edit_amount)
                    addDialogViewmodel.okClick(editName.text.toString() + "", editAmount.text.toString() + "")
                }
        return builder.create()

    }

    override fun getListRefresh(): ListRefresh {
        return activity as ListRefresh
    }

    override fun getDatabase(): DatabaseController {
        return DatabaseController(context)
    }

    override fun getToastDisplay(): ToastDisplay {
        return this
    }

    override fun showToast(toastId: Int) {
        when (toastId) {
            1 -> Toast.makeText(context, R.string.bad_name_amount, Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
        }
    }
    */
}

interface NoticeAddDialogListener{
    fun addNewCrypto( name:String,amount:Double)
}