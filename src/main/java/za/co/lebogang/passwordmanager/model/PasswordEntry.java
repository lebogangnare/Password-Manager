package za.co.lebogang.passwordmanager.model;

import java.util.UUID;

public class PasswordEntry {

    public static final String DEFAULT_CATEGORY = "General";

    private final String id;
    private String serviceName;
    private String username;
    private String password;
    private String notes;
    private String category;

    public PasswordEntry(String serviceName, String username, String password, String notes) {
        this(serviceName, username, password, notes, DEFAULT_CATEGORY);
    }

    public PasswordEntry(String serviceName, String username, String password, String notes, String category) {
        this.id = UUID.randomUUID().toString();
        this.serviceName = serviceName;
        this.username = username;
        this.password = password;
        this.notes = notes;
        setCategory(category);
    }

    public String getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCategory() {
        return category;
    }

   public void setCategory(String category) {
        if (category == null || category.isBlank()) {
            this.category = DEFAULT_CATEGORY;
        } else {
            this.category = category;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] %-15s user: %-25s pass: ********",
                id,
                serviceName,
                username
        );
    }
}