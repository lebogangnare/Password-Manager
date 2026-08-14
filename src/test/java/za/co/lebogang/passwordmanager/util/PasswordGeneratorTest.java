package za.co.lebogang.passwordmanager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    private final PasswordGenerator generator = new PasswordGenerator();

    @Test
    void generatedPasswordShouldHaveRequestedLength() {
        String password = generator.generate(12, true, true, true, true);

        assertEquals(12, password.length());
    }

    @Test
    void generatedPasswordShouldContainUppercase() {
        String password = generator.generate(12, true, true, true, true);

        assertTrue(password.chars().anyMatch(Character::isUpperCase));
    }

    @Test
    void generatedPasswordShouldContainLowercase() {
        String password = generator.generate(12, true, true, true, true);

        assertTrue(password.chars().anyMatch(Character::isLowerCase));
    }

    @Test
    void generatedPasswordShouldContainDigit() {
        String password = generator.generate(12, true, true, true, true);

        assertTrue(password.chars().anyMatch(Character::isDigit));
    }

    @Test
    void generatedPasswordShouldContainSymbol() {
        String password = generator.generate(12, true, true, true, true);

        assertTrue(password.chars().anyMatch(
                character -> "!@#$%^&*()-_=+".indexOf(character) >= 0
        ));
    }

    @Test
    void shouldThrowExceptionForLengthBelowFour() {
        assertThrows(
                IllegalArgumentException.class,
                () -> generator.generate(3, true, true, true, true)
        );
    }

    @Test
    void shouldThrowExceptionWhenNoCharacterTypesAreSelected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> generator.generate(12, false, false, false, false)
        );
    }

    @Test
    void shouldGenerateDefaultPasswordWithLengthSixteen() {
        String password = generator.generateDefault();

        assertEquals(16, password.length());
    }

    @Test
    void defaultPasswordShouldContainAllCharacterTypes() {
        String password = generator.generateDefault();

        assertTrue(password.chars().anyMatch(Character::isUpperCase));
        assertTrue(password.chars().anyMatch(Character::isLowerCase));
        assertTrue(password.chars().anyMatch(Character::isDigit));
        assertTrue(password.chars().anyMatch(
                character -> "!@#$%^&*()-_=+".indexOf(character) >= 0
        ));
    }
}
