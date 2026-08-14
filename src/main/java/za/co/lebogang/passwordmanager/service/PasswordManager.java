package za.co.lebogang.passwordmanager.service;

import za.co.lebogang.passwordmanager.model.PasswordEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PasswordManager {

    private final List<PasswordEntry> entries = new ArrayList<>();

    public PasswordEntry addEntry(
            String serviceName,
            String username,
            String password,
            String notes
    ) {
        return addEntry(
                serviceName,
                username,
                password,
                notes,
                PasswordEntry.DEFAULT_CATEGORY
        );
    }

    public PasswordEntry addEntry(
            String serviceName,
            String username,
            String password,
            String notes,
            String category
    ) {
        PasswordEntry entry = new PasswordEntry(
                serviceName,
                username,
                password,
                notes,
                category
        );

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

    public void loadEntry(PasswordEntry entry) {
        entries.add(entry);
    }

    public Optional<PasswordEntry> findById(String id) {

        for (PasswordEntry entry : entries) {

            if (entry.getId().equals(id)) {
                return Optional.of(entry);
            }
        }

        return Optional.empty();
    }

    public boolean updateEntry(
            String id,
            String serviceName,
            String username,
            String password,
            String notes,
            String category
    ) {

        Optional<PasswordEntry> optionalEntry = findById(id);

        if (optionalEntry.isEmpty()) {
            return false;
        }

        PasswordEntry entry = optionalEntry.get();

        if (serviceName != null && !serviceName.isBlank()) {
            entry.setServiceName(serviceName);
        }

        if (username != null && !username.isBlank()) {
            entry.setUsername(username);
        }

        if (password != null && !password.isBlank()) {
            entry.setPassword(password);
        }

        if (notes != null && !notes.isBlank()) {
            entry.setNotes(notes);
        }

        if (category != null && !category.isBlank()) {
            entry.setCategory(category);
        }

        return true;
    }

    public List<PasswordEntry> findByCategory(String category) {

        List<PasswordEntry> matches = new ArrayList<>();

        if (category == null || category.isBlank()) {
            return matches;
        }

        for (PasswordEntry entry : entries) {

            if (entry.getCategory().equalsIgnoreCase(category)) {
                matches.add(entry);
            }
        }

        return matches;
    }

    public Set<String> getCategories() {

        Set<String> categories = new HashSet<>();

        for (PasswordEntry entry : entries) {
            categories.add(entry.getCategory());
        }

        return categories;
    }
}