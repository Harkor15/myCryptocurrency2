package harkor.mycryptocurrency.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import harkor.mycryptocurrency.services.SharedPref
import android.content.Intent
import android.net.Uri
import harkor.mycryptocurrency.databinding.SettingsDialogBinding


class DialogSettings(val notifyCurrencyChange: NotifyCurrencyChange) : DialogFragment() {
    private lateinit var binding: SettingsDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = SettingsDialogBinding.inflate(layoutInflater)
            val currencyId = SharedPref.getDefaultCurrency(binding.root.context)
            when (currencyId) {
                1 -> binding.radioUsd.isChecked = true
                2 -> binding.radioEur.isChecked = true
                3 -> binding.radioPln.isChecked = true
                4 -> binding.radioBtc.isChecked = true
            }
            binding.settingsPrivacyPolicy.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://my-cryptocurrency.flycricket.io/privacy.html"))
                startActivity(browserIntent)
            }
            binding.settingsCancel.setOnClickListener {
                dialog?.cancel()
            }

            binding.settingsOk.setOnClickListener {
                val newCurrency:Int = when {
                    binding.radioUsd.isChecked -> 1
                    binding.radioEur.isChecked -> 2
                    binding.radioPln.isChecked -> 3
                    else -> 4
                }
                if(newCurrency!=currencyId){
                    SharedPref.saveDefaultCurrency(newCurrency,binding.root.context)
                    notifyCurrencyChange.change()
                }
                dialog?.cancel()
            }

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalAccessException("Activity cannot be null")
    }
}
