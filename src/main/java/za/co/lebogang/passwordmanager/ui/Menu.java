package za.co.lebogang.passwordmanager.ui;

import za.co.lebogang.passwordmanager.model.PasswordEntry;
import za.co.lebogang.passwordmanager.service.PasswordManager;
import za.co.lebogang.passwordmanager.storage.VaultStorage;
import za.co.lebogang.passwordmanager.util.PasswordGenerator;
import za.co.lebogang.passwordmanager.util.PasswordStrengthChecker;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final PasswordManager manager;
    private final VaultStorage vaultStorage;

    private final PasswordGenerator passwordGenerator = new PasswordGenerator();
    private final PasswordStrengthChecker strengthChecker =
            new PasswordStrengthChecker();

    private final Scanner scanner;

    public Menu(
            PasswordManager manager,
            VaultStorage vaultStorage,
            Scanner scanner
    ) {
        this.manager = manager;
        this.vaultStorage = vaultStorage;
        this.scanner = scanner;
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== PASSWORD MANAGER =====");
            System.out.println("1. Add new entry");
            System.out.println("2. View all entries");
            System.out.println("3. Search entries");
            System.out.println("4. Delete entry");
            System.out.println("5. Generate a password");
            System.out.println("6. Check a password's strength");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    handleAddEntry();
                    break;

                case "2":
                    handleViewAll();
                    break;

                case "3":
                    handleSearch();
                    break;

                case "4":
                    handleDelete();
                    break;

                case "5":
                    handleGeneratePassword();
                    break;

                case "6":
                    handleCheckStrength();
                    break;

                case "7":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleAddEntry() {

        System.out.print("Service Name: ");
        String serviceName = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print(
                "Password (leave blank to auto-generate): "
        );
        String password = scanner.nextLine();

        if (password.isBlank()) {
            password = passwordGenerator.generateDefault();
            System.out.println("Generated password: " + password);
        }

        System.out.print("Notes: ");
        String notes = scanner.nextLine();

        PasswordEntry entry = manager.addEntry(
                serviceName,
                username,
                password,
                notes
        );

        try {
            vaultStorage.save(manager.getAllEntries());
        } catch (GeneralSecurityException e) {
            System.out.println(
                    "Error saving vault: " + e.getMessage()
            );
        }

        System.out.println("Entry added successfully!");
        System.out.println(entry);
    }

    private void handleViewAll() {

        List<PasswordEntry> entries = manager.getAllEntries();

        if (entries.isEmpty()) {
            System.out.println("No entries saved yet.");
            return;
        }

        for (PasswordEntry entry : entries) {
            System.out.println(entry);
        }
    }

    private void handleSearch() {

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        List<PasswordEntry> results = manager.search(keyword);

        if (results.isEmpty()) {
            System.out.println("No matches found.");
            return;
        }

        for (PasswordEntry entry : results) {
            System.out.println(entry);
        }
    }

    private void handleDelete() {

        handleViewAll();

        System.out.print("Enter the ID of the entry to delete: ");
        String id = scanner.nextLine();

        boolean deleted = manager.deleteEntry(id);

        if (deleted) {
            try {
                vaultStorage.save(manager.getAllEntries());
                System.out.println("Entry deleted.");
            } catch (GeneralSecurityException e) {
                System.out.println(
                        "Error saving vault: " + e.getMessage()
                );
            }
        } else {
            System.out.println("No entry found with that ID.");
        }
    }

    private void handleGeneratePassword() {

        String password = passwordGenerator.generateDefault();

        System.out.println("Generated password: " + password);
        System.out.println(
                "Strength: " + strengthChecker.check(password)
        );
    }

    private void handleCheckStrength() {

        System.out.print("Enter a password to check: ");
        String password = scanner.nextLine();

        System.out.println(
                "Strength: " + strengthChecker.check(password)
        );
    }
}