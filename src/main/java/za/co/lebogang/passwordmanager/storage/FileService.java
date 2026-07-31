package za.co.lebogang.passwordmanager.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import za.co.lebogang.passwordmanager.model.PasswordEntry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    private static final String FILE_NAME = "passwords.json";

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public void save(List<PasswordEntry> entries) {

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            gson.toJson(entries, writer);

        } catch (IOException e) {

            throw new RuntimeException("Failed to save password entries.", e);

        }
    }

    public List<PasswordEntry> load() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {

            Type listType = new TypeToken<List<PasswordEntry>>() {}.getType();

            List<PasswordEntry> entries = gson.fromJson(reader, listType);

            if (entries == null) {
                return new ArrayList<>();
            }

            return entries;

        } catch (IOException e) {

            throw new RuntimeException("Failed to load password entries.", e);

        }
    }
}