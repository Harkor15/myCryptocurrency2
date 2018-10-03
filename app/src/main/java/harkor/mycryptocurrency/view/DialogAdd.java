package harkor.mycryptocurrency.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import harkor.mycryptocurrency.CryptoAdd;
import harkor.mycryptocurrency.R;
import harkor.mycryptocurrency.model.ListRefresh;
import harkor.mycryptocurrency.services.CryptoCheckAdd;
import harkor.mycryptocurrency.services.DatabaseController;

public class DialogAdd extends DialogFragment implements CryptoAdd{


Context context;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final View mView=getActivity().getLayoutInflater().inflate(R.layout.add_dialog,null);
        final CryptoAdd cryptoAdd=this;
        context=mView.getContext();
        builder.setView(mView)
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DialogAdd.this.getDialog().cancel();
                }
            })
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText editName= mView.findViewById(R.id.edit_name);
                    EditText editAmount=mView.findViewById(R.id.edit_amount);
                    String name=editName.getText()+"";
                    name=name.toUpperCase();
                    String amountS=editAmount.getText()+"";
                    double amount;
                    try{
                        amount= Double.parseDouble(amountS);
                        CryptoCheckAdd cryptoCheckAdd=new CryptoCheckAdd(name,amount,cryptoAdd,(ListRefresh)getActivity());
                        cryptoCheckAdd.check();
                    }catch (Error e){
                        Log.d("MyCrypto","name error");
                    }
                }
            });

        return builder.create();

    }

    @Override
    public void dbAddNewCrypto(String tag, double amount, String date, double priceUsd, double priceEur, double pricePln, double priceBtc) {
        DatabaseController db=new DatabaseController(context);
        db.addCrypto(tag,amount,date,priceUsd,priceEur,pricePln,priceBtc) ;
    }
}
