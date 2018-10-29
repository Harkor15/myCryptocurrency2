package harkor.mycryptocurrency.viewmodel;

import harkor.mycryptocurrency.Interfaces.SettingsDialogInterface;

public class SettingsDialogViewmodel {
    SettingsDialogInterface settingsDialogInterface;

    public SettingsDialogViewmodel(SettingsDialogInterface settingsDialogInterface) {
        this.settingsDialogInterface = settingsDialogInterface;
    }

    public void initially(){
        settingsDialogInterface.checkDefault(settingsDialogInterface.getCurrencyId());
    }

    public void okClick(){
        settingsDialogInterface.saveShared(settingsDialogInterface.whatIsChecked());
    }
}
