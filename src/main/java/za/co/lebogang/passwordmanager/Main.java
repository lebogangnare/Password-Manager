package za.co.lebogang.passwordmanager;

import za.co.lebogang.passwordmanager.model.PasswordEntry;
import za.co.lebogang.passwordmanager.service.PasswordManager;
import za.co.lebogang.passwordmanager.storage.FileService;
import za.co.lebogang.passwordmanager.ui.Menu;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        PasswordManager manager = new PasswordManager();

        FileService fileService = new FileService();

        List<PasswordEntry> entries = fileService.load();

        for (PasswordEntry entry : entries) {
            manager.addEntry(
                    entry.getServiceName(),
                    entry.getUsername(),
                    entry.getPassword(),
                    entry.getNotes()
            );
        }

        Menu menu = new Menu(manager, fileService);

        menu.start();
    }
}