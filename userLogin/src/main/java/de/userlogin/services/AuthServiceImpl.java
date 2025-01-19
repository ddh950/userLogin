package de.userlogin.services;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.Stateless;
import javax.inject.Inject;

import de.userlogin.persistence.UserDAO;
import de.userlogin.persistence.UserEntity;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@Stateless
public class AuthServiceImpl implements AuthService {

    @Inject
    UserDAO dao;

   // Check 
    public boolean validate(String enteredEmailAdress, String enteredPassword) {
        UserEntity userEntity = dao.findUserByMailadresse(enteredEmailAdress);

        if (userEntity == null) return false;
        else {
            boolean passwordIsCorrect = checkPassword(enteredPassword, userEntity);
            return passwordIsCorrect;
        }
    }

  
    private boolean checkPassword(String enteredPasswordPlainText, UserEntity userEntity) {
        byte[] storedPassword = userEntity.getEncryptedPassword();
        byte[] enteredPasswordEncrypted = encrypt(enteredPasswordPlainText.toCharArray(), userEntity.getSalt());

        return Arrays.equals(storedPassword, enteredPasswordEncrypted);
    }

   
     // Salt the given password and encrypt with PBKDF2WithHmacSHA512 algorithm.
     
    public byte[] encrypt(char[] passwordPlainText, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(passwordPlainText, salt, 1000, 512);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512"); // Important phrase
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateSalt() {
        byte[] salt = new byte[64];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

}
