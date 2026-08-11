package za.co.lebogang.passwordmanager.security;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class KeyDerivation {

    public static final int SALT_LENGTH_BYTES = 16;
    public static final int DEFAULT_ITERATIONS = 210_000;

    private KeyDerivation() {
        // Utility class - prevent instantiation
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static SecretKey deriveKey(char[] password, byte[] salt, int iterations)
            throws GeneralSecurityException {

        PBEKeySpec spec = new PBEKeySpec(
                password,
                salt,
                iterations,
                256
        );

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] keyBytes = factory.generateSecret(spec).getEncoded();

            return new SecretKeySpec(keyBytes, "AES");
        } finally {
            spec.clearPassword();
        }
    }
}

