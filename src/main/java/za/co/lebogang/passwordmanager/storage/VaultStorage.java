package za.co.lebogang.passwordmanager.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import za.co.lebogang.passwordmanager.model.PasswordEntry;
import za.co.lebogang.passwordmanager.security.CryptoService;
import za.co.lebogang.passwordmanager.security.KeyDerivation;

import javax.crypto.AEADBadTagException;
import javax.crypto.SecretKey;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class VaultStorage {

    private static final Path VAULT_PATH = Path.of("vault.json");

    private final Gson gson;
    private final CryptoService cryptoService;

    private SecretKey key;
    private byte[] salt;
    private int iterations;

    public VaultStorage() {
        this.gson = new Gson();
        this.cryptoService = new CryptoService();
    }

    public boolean vaultExists() {
        return Files.exists(VAULT_PATH);
    }

    public void createVault(char[] masterPassword)
            throws GeneralSecurityException {

        salt = KeyDerivation.generateSalt();
        iterations = KeyDerivation.DEFAULT_ITERATIONS;

        key = KeyDerivation.deriveKey(
                masterPassword,
                salt,
                iterations
        );

        save(Collections.emptyList());
    }

    public List<PasswordEntry> unlock(char[] masterPassword)
            throws GeneralSecurityException {

        try {
            String json = Files.readString(VAULT_PATH);

            VaultData vaultData = gson.fromJson(json, VaultData.class);

            salt = Base64.getDecoder().decode(vaultData.salt);
            iterations = vaultData.iterations;

            key = KeyDerivation.deriveKey(
                    masterPassword,
                    salt,
                    iterations
            );

            byte[] ivAndCiphertext =
                    Base64.getDecoder().decode(vaultData.payload);

            byte[] plaintext =
                    cryptoService.decrypt(ivAndCiphertext, key);

            String entriesJson =
                    new String(plaintext, StandardCharsets.UTF_8);

            Type listType =
                    new TypeToken<List<PasswordEntry>>() {}.getType();

            return gson.fromJson(entriesJson, listType);

        } catch (AEADBadTagException e) {
            throw new IllegalArgumentException(
                    "Incorrect master password",
                    e
            );
        } catch (java.io.IOException e) {
            throw new RuntimeException(
                    "Failed to read vault",
                    e
            );
        }
    }

    public void save(List<PasswordEntry> entries)
            throws GeneralSecurityException {

        if (key == null || salt == null) {
            throw new IllegalStateException(
                    "Vault is not unlocked"
            );
        }

        String entriesJson = gson.toJson(entries);

        byte[] plaintext =
                entriesJson.getBytes(StandardCharsets.UTF_8);

        byte[] ivAndCiphertext =
                cryptoService.encrypt(plaintext, key);

        VaultData vaultData = new VaultData(
                Base64.getEncoder().encodeToString(salt),
                iterations,
                Base64.getEncoder().encodeToString(ivAndCiphertext)
        );

        String vaultJson = gson.toJson(vaultData);

        try {
            Files.writeString(VAULT_PATH, vaultJson);
        } catch (java.io.IOException e) {
            throw new RuntimeException(
                    "Failed to save vault",
                    e
            );
        }
    }

    private record VaultData(String salt, int iterations, String payload) {

    }
}