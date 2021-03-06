package de.belmega.biohazard.auth.common;


import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

public class EncryptionUtil {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 512;

    /**
     * Return a random salt to hash a password.
     *
     * @return a 32 bytes random salt
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[64];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Returns a salted and hashed password using PBKDF2WithHmacSHA512.
     *
     * @param password the password to be hashed
     * @param salt     a 64 bytes salt
     * @return the hashed password with a pinch of salt
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static byte[] generateRandomPassword(byte[] salt) {
        String password = RandomStringUtils.random(20, CHARACTERS);
        return hash(password.toCharArray(), salt);
    }
}
