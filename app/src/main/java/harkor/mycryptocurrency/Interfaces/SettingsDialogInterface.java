package harkor.mycryptocurrency.Interfaces;

public interface SettingsDialogInterface {
    int getCurrencyId();
    void checkDefault(int checked);
    int whatIsChecked();
    void saveShared(int currencyId);
}
