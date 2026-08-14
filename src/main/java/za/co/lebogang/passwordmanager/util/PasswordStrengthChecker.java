package za.co.lebogang.passwordmanager.util;

import java.util.Set;

public class PasswordStrengthChecker {

    public enum Strength {
        VERY_WEAK,
        WEAK,
        MEDIUM,
        STRONG,
        VERY_STRONG
    }

    private static final Set<String> COMMON_PASSWORDS = Set.of("password", "123456", "12345678", "qwerty","password1","admin","letmein","welcome");

    public Strength check(String password) {

        if (password == null || password.isEmpty()) {
            return Strength.VERY_WEAK;
        }

        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            return Strength.VERY_WEAK;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSymbol = false;

        for (char character : password.toCharArray()) {

            if (Character.isUpperCase(character)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(character)) {
                hasLowercase = true;
            } else if (Character.isDigit(character)) {
                hasDigit = true;
            } else {
                hasSymbol = true;
            }
        }

        if (password.length() < 8 || password.length() > 16) {
            return Strength.VERY_WEAK;
        }

        int score = 0;

        if (password.length() >= 8) {
            score++;
        }

        if (password.length() >= 12) {
            score++;
        }

        if (password.length() == 16) {
            score++;
        }

        if (hasUppercase) {
            score++;
        }

        if (hasLowercase) {
            score++;
        }

        if (hasDigit) {
            score++;
        }

        if (hasSymbol) {
            score++;
        }

        if (!hasUppercase || !hasLowercase || !hasDigit || !hasSymbol) {
            return score <= 2
                    ? Strength.WEAK
                    : Strength.MEDIUM;
        }

        if (score >= 7) {
            return Strength.VERY_STRONG;
        }

        if (score >= 6) {
            return Strength.STRONG;
        }

        return Strength.MEDIUM;
    }
}