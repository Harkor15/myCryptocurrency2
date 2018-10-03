package harkor.mycryptocurrency.view;

import android.content.SharedPreferences;

import harkor.mycryptocurrency.model.Cryptocurrency;

public interface AdvancedDialogInterface {
    void setNameText(String text);

    void setAmountText(String text);

    void setTimeText(String text);

    void setAddPriceText(String text);

    void setAddValueText(String text);

    void setBalanceText(String text);

    void setBalancePercentText(String text);

    void setActualPriceText(String text);

    void setActualValueText(String text);

    int getCurrencyCode();

    Cryptocurrency getCrypto();
}
