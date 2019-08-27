package harkor.mycryptocurrency.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import harkor.mycryptocurrency.Interfaces.SettingsDialogInterface;
import harkor.mycryptocurrency.R;
import harkor.mycryptocurrency.viewmodel.SettingsDialogViewmodel;

public class DialogSettings extends DialogFragment implements SettingsDialogInterface{
    RadioButton radioBtc;
    RadioButton radioUsd;
    RadioButton radioEur;
    RadioButton radioPln;
    View mView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        mView=getActivity().getLayoutInflater().inflate(R.layout.settings_dialog,null);
        radioBtc=mView.findViewById(R.id.radio_btc);
        radioUsd=mView.findViewById(R.id.radio_usd);
        radioEur=mView.findViewById(R.id.radio_eur);
        radioPln=mView.findViewById(R.id.radio_pln);
        final SettingsDialogViewmodel settingsDialogViewmodel=new SettingsDialogViewmodel(this);
        settingsDialogViewmodel.initially();
        builder.setView(mView)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        settingsDialogViewmodel.okClick();
                    }
                });
        return builder.create();
    }

    @Override
    public int getCurrencyId() {
        final SharedPreferences sharedPref =mView.getContext().getSharedPreferences("harkor.myCryptocurrency", Context.MODE_PRIVATE);
        return sharedPref.getInt("currencyCode",1);
    }

    @Override
    public void checkDefault(int checked) {
        switch (checked){
            case 1: radioUsd.setChecked(true); break;
            case 2: radioEur.setChecked(true); break;
            case 3: radioPln.setChecked(true); break;
            case 4: radioBtc.setChecked(true); break;
        }
    }

    @Override
    public int whatIsChecked() {
        if(radioUsd.isChecked()){
            return 1;
        }else if(radioEur.isChecked()){
            return 2;
        }else if(radioPln.isChecked()){
            return 3;
        }else{
            return 4;
        }
    }

    @Override
    public void saveShared(int currencyId) {
        final SharedPreferences sharedPref =mView.getContext().getSharedPreferences("harkor.myCryptocurrency", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putInt("currencyCode",currencyId);
        editor.apply();
    }
}
