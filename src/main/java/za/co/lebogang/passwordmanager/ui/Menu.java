package za.co.lebogang.passwordmanager.ui;

import za.co.lebogang.passwordmanager.model.PasswordEntry;
import za.co.lebogang.passwordmanager.service.PasswordManager;
import za.co.lebogang.passwordmanager.storage.FileService;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final PasswordManager manager;
    private final FileService fileService;

    private final Scanner scanner = new Scanner(System.in);

    public Menu(PasswordManager manager, FileService fileService) {
        this.manager = manager;
        this.fileService = fileService;
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== PASSWORD MANAGER =====");
            System.out.println("1. Add new entry");
            System.out.println("2. View all entries");
            System.out.println("3. Search entries");
            System.out.println("4. Delete entry");
            System.out.println("5. Exit");
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

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Notes: ");
        String notes = scanner.nextLine();

        PasswordEntry entry = manager.addEntry(
                serviceName,
                username,
                password,
                notes
        );

        fileService.save(manager.getAllEntries());

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
            fileService.save(manager.getAllEntries());
            System.out.println("Entry deleted.");
        } else {
            System.out.println("No entry found with that ID.");
        }
    }
}