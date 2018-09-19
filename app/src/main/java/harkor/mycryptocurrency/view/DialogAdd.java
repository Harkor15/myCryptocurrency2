package harkor.mycryptocurrency.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import harkor.mycryptocurrency.R;

public class DialogAdd extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final View mView=getActivity().getLayoutInflater().inflate(R.layout.add_dialog,null);
        final LayoutInflater inflater=getActivity().getLayoutInflater();
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
                    String amountS=editAmount.getText()+"";
                    Double amounnt;
                    if(!amountS.isEmpty())
                        try{
                            amounnt= Double.parseDouble(amountS);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        //TODO: CHECK DATA
                        //TODO: CHECK PRICEC
                        //TODO: ADD TO DATABASE

                }
            });

        return builder.create();
    }
}
