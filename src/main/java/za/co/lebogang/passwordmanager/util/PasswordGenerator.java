package za.co.lebogang.passwordmanager.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijkmnopqrstuvwxyz";
    private static final String DIGITS = "23456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+";

    private final SecureRandom random = new SecureRandom();

    public String generate(int length, boolean useUpper, boolean useLower, boolean useDigits, boolean useSymbols) {

        if (length < 4) {
            throw new IllegalArgumentException("Password length must be at least 4.");
        }

        if (!useUpper && !useLower && !useDigits && !useSymbols) {
            throw new IllegalArgumentException("At least one character type must be selected.");
        }

        List<Character> password = new ArrayList<>();
        StringBuilder pool = new StringBuilder();

        if (useUpper) {
            password.add(randomCharacter(UPPERCASE));
            pool.append(UPPERCASE);
        }

        if (useLower) {
            password.add(randomCharacter(LOWERCASE));
            pool.append(LOWERCASE);
        }

        if (useDigits) {
            password.add(randomCharacter(DIGITS));
            pool.append(DIGITS);
        }

        if (useSymbols) {
            password.add(randomCharacter(SYMBOLS));
            pool.append(SYMBOLS);
        }

        while (password.size() < length) {
            password.add(randomCharacter(pool.toString()));
        }

        Collections.shuffle(password, random);

        StringBuilder result = new StringBuilder();

        for (char character : password) {
            result.append(character);
        }

        return result.toString();
    }

    public String generateDefault() {
        return generate(16, true, true, true, true);
    }

    private char randomCharacter(String characters) {
        return characters.charAt(random.nextInt(characters.length()));
    }
}