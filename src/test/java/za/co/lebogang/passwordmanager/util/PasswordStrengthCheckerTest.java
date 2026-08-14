package za.co.lebogang.passwordmanager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordStrengthCheckerTest {

    private final PasswordStrengthChecker checker = new PasswordStrengthChecker();

    @Test
    void nullPasswordShouldBeVeryWeak() {
        assertEquals(
                PasswordStrengthChecker.Strength.VERY_WEAK,
                checker.check(null)
        );
    }

    @Test
    void emptyPasswordShouldBeVeryWeak() {
        assertEquals(
                PasswordStrengthChecker.Strength.VERY_WEAK,
                checker.check("")
        );
    }

    @Test
    void commonPasswordShouldBeVeryWeak() {
        assertEquals(
                PasswordStrengthChecker.Strength.VERY_WEAK,
                checker.check("password")
        );
    }

    @Test
    void passwordWithAllRequiredCharacterTypesShouldNotBeWeak() {
        assertEquals(
                PasswordStrengthChecker.Strength.MEDIUM,
                checker.check("Password1!")
        );
    }

    @Test
    void strongPasswordShouldBeStrong() {
        assertEquals(
                PasswordStrengthChecker.Strength.STRONG,
                checker.check("MyPassword123!")
        );
    }

    @Test
    void passwordMissingUppercaseShouldNotBeStrong() {
        assertEquals(
                PasswordStrengthChecker.Strength.MEDIUM,
                checker.check("mypassword1!")
        );
    }

    @Test
    void passwordMissingLowercaseShouldNotBeStrong() {
        assertEquals(
                PasswordStrengthChecker.Strength.MEDIUM,
                checker.check("MYPASSWORD1!")
        );
    }

    @Test
    void passwordMissingDigitShouldNotBeStrong() {
        assertEquals(
                PasswordStrengthChecker.Strength.MEDIUM,
                checker.check("MyPassword!")
        );
    }

    @Test
    void passwordMissingSymbolShouldNotBeStrong() {
        assertEquals(
                PasswordStrengthChecker.Strength.MEDIUM,
                checker.check("MyPassword1")
        );
    }

    @Test
    void passwordTooShortShouldBeVeryWeak() {
        assertEquals(
                PasswordStrengthChecker.Strength.VERY_WEAK,
                checker.check("Pass1!")
        );
    }

    @Test
    void passwordTooLongShouldBeVeryWeak() {
        assertEquals(
                PasswordStrengthChecker.Strength.VERY_WEAK,
                checker.check("MyVeryLongPassword123!")
        );
    }
}