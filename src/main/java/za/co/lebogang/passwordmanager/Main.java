package za.co.lebogang.passwordmanager;

import za.co.lebogang.passwordmanager.model.PasswordEntry;
import za.co.lebogang.passwordmanager.service.PasswordManager;
import za.co.lebogang.passwordmanager.storage.VaultStorage;
import za.co.lebogang.passwordmanager.ui.Menu;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int MAX_UNLOCK_ATTEMPTS = 3;
    private static final int MIN_PASSWORD_LENGTH = 8;

    public static void main(String[] args) {

        VaultStorage vaultStorage = new VaultStorage();
        PasswordManager manager = new PasswordManager();
        Scanner scanner = new Scanner(System.in);

        try {

            if (!vaultStorage.vaultExists()) {

                System.out.println("No vault found.");
                System.out.println("Let's create your password vault.");

                char[] masterPassword = promptForNewPassword(scanner);

                try {
                    vaultStorage.createVault(masterPassword);
                    System.out.println("Vault created successfully.");
                } finally {
                    Arrays.fill(masterPassword, ' ');
                }

            } else {

                System.out.println("Password vault found.");

                List<PasswordEntry> entries =
                        unlockVault(vaultStorage, scanner);

                if (entries == null) {
                    System.out.println("Too many failed attempts.");
                    System.out.println("Exiting...");
                    return;
                }

                for (PasswordEntry entry : entries) {
                    manager.loadEntry(entry);
                }

                System.out.println("Vault unlocked successfully.");
                System.out.println("Loaded " + entries.size() + " entries.");
            }

            new Menu(manager, vaultStorage, scanner).start();

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static char[] promptForNewPassword(Scanner scanner) {

        while (true) {

            char[] password = readPassword(
                    scanner, "Create master password: "
            );

            if (password == null) {
                throw new IllegalStateException(
                        "Unable to read master password."
                );
            }

            if (password.length < MIN_PASSWORD_LENGTH) {

                System.out.println(
                        "Password must be at least "
                                + MIN_PASSWORD_LENGTH
                                + " characters."
                );

                Arrays.fill(password, ' ');
                continue;
            }

            char[] confirmation = readPassword(
                    scanner, "Confirm master password: "
            );

            if (confirmation == null) {

                Arrays.fill(password, ' ');

                throw new IllegalStateException(
                        "Unable to read password confirmation."
                );
            }

            if (!Arrays.equals(password, confirmation)) {

                System.out.println("Passwords do not match.");

                Arrays.fill(password, ' ');
                Arrays.fill(confirmation, ' ');

                continue;
            }

            Arrays.fill(confirmation, ' ');

            return password;
        }
    }

    private static List<PasswordEntry> unlockVault(
            VaultStorage vaultStorage,
            Scanner scanner
    ) {

        for (
                int attempt = 1;
                attempt <= MAX_UNLOCK_ATTEMPTS;
                attempt++
        ) {

            System.out.println(
                    "Unlock attempt "
                            + attempt
                            + " of "
                            + MAX_UNLOCK_ATTEMPTS
            );

            char[] password = readPassword(
                    scanner, "Master password: "
            );

            if (password == null) {

                System.out.println(
                        "Unable to read password."
                );

                return null;
            }

            try {

                return vaultStorage.unlock(password);

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Incorrect master password."
                );

                if (attempt < MAX_UNLOCK_ATTEMPTS) {
                    System.out.println(
                            "Please try again."
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "Unable to unlock vault: "
                                + e.getMessage()
                );

                return null;

            } finally {

                Arrays.fill(password, ' ');
            }
        }

        return null;
    }

    private static char[] readPassword(
            Scanner scanner,
            String prompt
    ) {

        Console console = System.console();

        if (console != null) {
            return console.readPassword(prompt);
        }

        System.out.print(prompt);

        return scanner.nextLine().toCharArray();
    }
}