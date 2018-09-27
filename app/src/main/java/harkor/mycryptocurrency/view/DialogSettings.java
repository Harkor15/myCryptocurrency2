package harkor.mycryptocurrency.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import harkor.mycryptocurrency.R;

public class DialogSettings extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //DialogSettings.Builder
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final View mView=getActivity().getLayoutInflater().inflate(R.layout.settings_dialog,null);
        //final LayoutInflater inflater=getActivity().getLayoutInflater();
        final SharedPreferences sharedPref =mView.getContext().getSharedPreferences("harkor.myCryptocurrency", Context.MODE_PRIVATE);
        final int currencyCode=sharedPref.getInt("currencyCode",1);
        RadioButton radioBtc=mView.findViewById(R.id.radio_btc);
        final RadioButton radioUsd=mView.findViewById(R.id.radio_usd);
        final RadioButton radioEur=mView.findViewById(R.id.radio_eur);
        final RadioButton radioPln=mView.findViewById(R.id.radio_pln);
        switch (currencyCode){
            case 1: radioUsd.setChecked(true); break;
            case 2: radioEur.setChecked(true); break;
            case 3: radioPln.setChecked(true); break;
            case 4: radioBtc.setChecked(true); break;
        }
        builder.setView(mView)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DialogSettings.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor=sharedPref.edit();
                        if(radioUsd.isChecked()){
                            editor.putInt("currencyCode",1);
                        }else if(radioEur.isChecked()){
                            editor.putInt("currencyCode",2);
                        }else if(radioPln.isChecked()){
                            editor.putInt("currencyCode",3);
                        }else{
                            editor.putInt("currencyCode",4);
                        }
                        editor.apply();
                    }
                });
        return builder.create();

    }
}
