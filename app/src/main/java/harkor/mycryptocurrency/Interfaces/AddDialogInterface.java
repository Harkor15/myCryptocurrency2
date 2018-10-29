package harkor.mycryptocurrency.Interfaces;

import harkor.mycryptocurrency.services.DatabaseController;

public interface AddDialogInterface {
    ListRefresh getListRefresh();
    DatabaseController getDatabase();
    ToastDisplay getToastDisplay();
}
