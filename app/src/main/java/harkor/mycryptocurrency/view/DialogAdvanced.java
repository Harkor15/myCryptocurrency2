package harkor.mycryptocurrency.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import harkor.mycryptocurrency.R;
import harkor.mycryptocurrency.model.Cryptocurrency;
import harkor.mycryptocurrency.services.DatabaseController;
import harkor.mycryptocurrency.viewmodel.AdvancedDialogViewModel;

public class DialogAdvanced extends DialogFragment implements AdvancedDialogInterface{
    Context context;
    @BindView(R.id.text_name) TextView nameText;
    @BindView(R.id.text_amount) TextView amountText;
    @BindView(R.id.text_time) TextView timeText;
    @BindView(R.id.text_add_price) TextView addPriceText;
    @BindView(R.id.text_add_value) TextView addValueText;
    @BindView(R.id.text_balance) TextView balanceText;
    @BindView(R.id.text_balance_percent) TextView balancePercentText;
    @BindView(R.id.text_actual_price) TextView actualPriceText;
    @BindView(R.id.text_actual_value) TextView actualValueText;

    int cryptoID;
    int currencyTag;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        cryptoID=getArguments().getInt("idCrypto");
        final View mView=getActivity().getLayoutInflater().inflate(R.layout.advanced_dialog,null);
        context=mView.getContext();
        ButterKnife.bind(this, mView);
        SharedPreferences sharedPreferences=context.getSharedPreferences("harkor.myCryptocurrency",Context.MODE_PRIVATE);
        currencyTag=sharedPreferences.getInt("currencyCode",1);
        AdvancedDialogViewModel advancedDialogViewModel=new AdvancedDialogViewModel(this);
        advancedDialogViewModel.startViewModel();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(mView)
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO: 2 second and delete
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DialogAdvanced.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void setNameText(String text) {
        nameText.setText(text);
    }

    @Override
    public void setAmountText(String text) {
        amountText.setText(text);
    }

    @Override
    public void setTimeText(String text) {
        timeText.setText(text);
    }

    @Override
    public void setAddPriceText(String text) {
        addPriceText.setText(text);
    }

    @Override
    public void setAddValueText(String text) {
        addValueText.setText(text);
    }

    @Override
    public void setBalanceText(String text) {
        balanceText.setText(text);
    }

    @Override
    public void setBalancePercentText(String text) {
        balancePercentText.setText(text);
    }

    @Override
    public void setActualPriceText(String text) {
        actualPriceText.setText(text);
    }

    @Override
    public void setActualValueText(String text) {
        actualValueText.setText(text);
    }

    @Override
    public int getCurrencyCode() {
        return currencyTag;
    }

    @Override
    public Cryptocurrency getCrypto() {
        DatabaseController db=new DatabaseController(context);
        return db.singleCrypto(cryptoID);
    }
}