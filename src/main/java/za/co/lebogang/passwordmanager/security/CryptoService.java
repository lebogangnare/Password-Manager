package za.co.lebogang.passwordmanager.security;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

public class CryptoService {

    private static final int IV_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;

    private final SecureRandom secureRandom = new SecureRandom();

    public byte[] encrypt(byte[] plaintext, SecretKey key)
            throws GeneralSecurityException {

        byte[] iv = new byte[IV_LENGTH_BYTES];
        secureRandom.nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec parameterSpec =
                new GCMParameterSpec(TAG_LENGTH_BITS, iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        byte[] ciphertext = cipher.doFinal(plaintext);

        byte[] ivAndCiphertext =
                new byte[IV_LENGTH_BYTES + ciphertext.length];

        System.arraycopy(
                iv,
                0,
                ivAndCiphertext,
                0,
                IV_LENGTH_BYTES
        );

        System.arraycopy(
                ciphertext,
                0,
                ivAndCiphertext,
                IV_LENGTH_BYTES,
                ciphertext.length
        );

        return ivAndCiphertext;
    }

    public byte[] decrypt(byte[] ivAndCiphertext, SecretKey key)
            throws GeneralSecurityException, AEADBadTagException {

        if (ivAndCiphertext.length < IV_LENGTH_BYTES) {
            throw new IllegalArgumentException(
                    "Encrypted data is too short to contain an IV"
            );
        }

        byte[] iv = Arrays.copyOfRange(
                ivAndCiphertext,
                0,
                IV_LENGTH_BYTES
        );

        byte[] ciphertext = Arrays.copyOfRange(
                ivAndCiphertext,
                IV_LENGTH_BYTES,
                ivAndCiphertext.length
        );

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec parameterSpec =
                new GCMParameterSpec(TAG_LENGTH_BITS, iv);

        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

        return cipher.doFinal(ciphertext);
    }
}

