/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {
    
    public static String hashPassword(String password) {
        if (password == null) {
            System.err.println("PasswordUtils.hashPassword: Input password was null.");
            return null; 
        }
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            
            byte[] combined = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);
            
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("PasswordUtils.hashPassword: NoSuchAlgorithmException - " + e.getMessage());
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    public static boolean verifyPassword(String plainTextPassword, String storedHashedPassword) {
        if (plainTextPassword == null) {
            System.err.println("PasswordUtils.verifyPassword: Input plainTextPassword was null.");
            return false;
        }
        if (storedHashedPassword == null) {
            System.err.println("PasswordUtils.verifyPassword: Input storedHashedPassword was null.");
            return false;
        }

        try {
            byte[] combined = Base64.getDecoder().decode(storedHashedPassword);
            
            if (combined.length < 16) {
                System.err.println("PasswordUtils.verifyPassword: Stored hashed password is too short after decoding. Length: " + combined.length);
                return false;
            }
            
            byte[] salt = new byte[16];
            byte[] hash = new byte[combined.length - 16];
            System.arraycopy(combined, 0, salt, 0, 16);
            System.arraycopy(combined, 16, hash, 0, hash.length);
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] newHash = md.digest(plainTextPassword.getBytes());
            
            return MessageDigest.isEqual(hash, newHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("PasswordUtils.verifyPassword: NoSuchAlgorithmException - " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) { 
            System.err.println("PasswordUtils.verifyPassword: IllegalArgumentException (likely invalid Base64 stored password: '" + storedHashedPassword + "') - " + e.getMessage());
return false;
        } catch (Exception e) { 
            System.err.println("PasswordUtils.verifyPassword: Unexpected exception while verifying password '" + plainTextPassword + "' against stored '" + storedHashedPassword + "'. Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
