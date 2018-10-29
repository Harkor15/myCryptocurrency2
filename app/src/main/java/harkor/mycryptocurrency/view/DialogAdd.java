package harkor.mycryptocurrency.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import harkor.mycryptocurrency.Interfaces.AddDialogInterface;
import harkor.mycryptocurrency.Interfaces.CryptoAdd;
import harkor.mycryptocurrency.Interfaces.ListRefresh;
import harkor.mycryptocurrency.Interfaces.ToastDisplay;
import harkor.mycryptocurrency.R;
import harkor.mycryptocurrency.services.CryptoCheckAdd;
import harkor.mycryptocurrency.services.DatabaseController;
import harkor.mycryptocurrency.viewmodel.AddDialogViewmodel;

public class DialogAdd extends DialogFragment implements AddDialogInterface, ToastDisplay{

Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AddDialogViewmodel addDialogViewmodel=new AddDialogViewmodel(this);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final View mView=getActivity().getLayoutInflater().inflate(R.layout.add_dialog,null);
        context=mView.getContext();
        builder.setView(mView)
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            })
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText editName= mView.findViewById(R.id.edit_name);
                    EditText editAmount=mView.findViewById(R.id.edit_amount);
                    addDialogViewmodel.okClick(editName.getText()+"",editAmount.getText()+"");
                }
            });
        return builder.create();

    }

    @Override
    public ListRefresh getListRefresh() {
        return (ListRefresh)getActivity();
    }

    @Override
    public DatabaseController getDatabase() {
        return new DatabaseController(context);
    }

    @Override
    public ToastDisplay getToastDisplay() {
        return this;
    }

    @Override
    public void showToast(int toastId) {
        switch (toastId){
            case 1:
                Toast.makeText(context,R.string.bad_name_amount,Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(context,R.string.connection_error,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
