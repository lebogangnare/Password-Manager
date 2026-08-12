package za.co.lebogang.passwordmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.lebogang.passwordmanager.model.PasswordEntry;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PasswordManagerTest {

    private PasswordManager manager;

    @BeforeEach
    void setUp() {
        manager = new PasswordManager();
    }

    @Test
    void addEntry_shouldAddEntryToList() {
        manager.addEntry(
                "Gmail",
                "lebo@gmail.com",
                "pass123",
                "personal"
        );

        assertEquals(1, manager.getAllEntries().size());
    }

    @Test
    void addEntry_shouldReturnEntryWithCorrectFields() {
        PasswordEntry entry = manager.addEntry(
                "Gmail",
                "lebo@gmail.com",
                "pass123",
                "personal"
        );

        assertEquals("Gmail", entry.getServiceName());
        assertEquals("lebo@gmail.com", entry.getUsername());
    }

    @Test
    void search_shouldFindMatchingEntries() {
        manager.addEntry(
                "Gmail",
                "lebo@gmail.com",
                "pass123",
                "personal"
        );

        manager.addEntry(
                "GitHub",
                "lebo@github.com",
                "pass456",
                "coding"
        );

        List<PasswordEntry> results = manager.search("git");

        assertEquals(1, results.size());
        assertEquals("GitHub", results.get(0).getServiceName());
    }

    @Test
    void search_shouldReturnEmptyListWhenNoMatch() {
        manager.addEntry(
                "Gmail",
                "lebo@gmail.com",
                "pass123",
                "personal"
        );

        List<PasswordEntry> results = manager.search("Netflix");

        assertTrue(results.isEmpty());
    }

    @Test
    void deleteEntry_shouldRemoveEntryAndReturnTrue() {
        PasswordEntry entry = manager.addEntry(
                "Gmail",
                "lebo@gmail.com",
                "pass123",
                "personal"
        );

        boolean deleted = manager.deleteEntry(entry.getId());

        assertTrue(deleted);
        assertEquals(0, manager.getAllEntries().size());
    }

    @Test
    void deleteEntry_shouldReturnFalseForUnknownId() {
        boolean deleted = manager.deleteEntry(
                "some-fake-id-that-doesnt-exist"
        );

        assertFalse(deleted);
    }
}