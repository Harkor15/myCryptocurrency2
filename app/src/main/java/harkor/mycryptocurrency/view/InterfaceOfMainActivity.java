package harkor.mycryptocurrency.view;

import android.app.FragmentManager;

import harkor.mycryptocurrency.services.DatabaseController;

public interface InterfaceOfMainActivity {
    FragmentManager fragmentMenagerGetter();
    DatabaseController databaseGetter();
}
