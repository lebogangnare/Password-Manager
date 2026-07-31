package za.co.lebogang.passwordmanager.model;


import java.util.UUID;

public class PasswordEntry {

    private final String id;
    private String serviceName;
    private String username;
    private String password;
    private String notes;


    public PasswordEntry(String serviceName, String username, String password, String notes) {
        this.id = UUID.randomUUID().toString();
        this.serviceName = serviceName;
        this.username = username;
        this.password = password;
        this.notes = notes;
    }

    public PasswordEntry(String id, String serviceName, String username, String password, String notes){
        this.id = id;
        this.serviceName = serviceName;
        this.username = username;
        this.password = password;
        this.notes = notes;
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