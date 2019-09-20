package harkor.mycryptocurrency.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import harkor.mycryptocurrency.services.SharedPref
import kotlinx.android.synthetic.main.settings_dialog.view.*
import android.content.Intent
import android.net.Uri
import harkor.mycryptocurrency.R


class DialogSettings(val notifyCurrencyChange: NotifyCurrencyChange) : DialogFragment() {
    lateinit var dialogView: View
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            dialogView = LayoutInflater.from(activity).inflate(R.layout.settings_dialog, null)
            val currencyId = SharedPref.getDefaultCurrency(dialogView.context)
            when (currencyId) {
                1 -> dialogView.radio_usd.isChecked = true
                2 -> dialogView.radio_eur.isChecked = true
                3 -> dialogView.radio_pln.isChecked = true
                4 -> dialogView.radio_btc.isChecked = true
            }
            dialogView.settings_privacy_policy.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://my-cryptocurrency.flycricket.io/privacy.html"))
                startActivity(browserIntent)
            }
            dialogView.settings_cancel.setOnClickListener {
                dialog.cancel()
            }

            dialogView.settings_ok.setOnClickListener {
                val newCurrency:Int = when {
                    dialogView.radio_usd.isChecked -> 1
                    dialogView.radio_eur.isChecked -> 2
                    dialogView.radio_pln.isChecked -> 3
                    else -> 4
                }
                if(newCurrency!=currencyId){
                    SharedPref.saveDefaultCurrency(newCurrency,dialogView.context)
                    notifyCurrencyChange.change()
                }
                dialog.cancel()
            }

            builder.setView(dialogView)
            builder.create()
        } ?: throw IllegalAccessException("Activity cannot be null")
    }
}
