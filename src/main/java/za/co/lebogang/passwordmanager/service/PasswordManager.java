package za.co.lebogang.passwordmanager.service;

import za.co.lebogang.passwordmanager.model.PasswordEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PasswordManager {

    private final List<PasswordEntry> entries = new ArrayList<>();


    public PasswordEntry addEntry(String serviceName, String username, String password, String notes) {

        PasswordEntry entry = new PasswordEntry(serviceName, username, password, notes);

        entries.add(entry);

        return entry;
    }


    public List<PasswordEntry> getAllEntries() {
        return new ArrayList<>(entries);
    }


    public List<PasswordEntry> search(String keyword) {

        List<PasswordEntry> matches = new ArrayList<>();

        String searchTerm = keyword.toLowerCase();

        for (PasswordEntry entry : entries) {

            if (entry.getServiceName().toLowerCase().contains(searchTerm)
                    || entry.getUsername().toLowerCase().contains(searchTerm)) {

                matches.add(entry);
            }
        }

        return matches;
    }

    public boolean deleteEntry(String id) {
        return entries.removeIf(entry -> entry.getId().equals(id));
    }

    public Optional<PasswordEntry> findById(String id) {

        for (PasswordEntry entry : entries) {

            if (entry.getId().equals(id)) {
                return Optional.of(entry);
            }
        }

        return Optional.empty();
    }
}