package de.userlogin.services;

public interface AuthService {

    boolean validate(String emailAdress, String password);

    byte[] encrypt(char[] passwordPlainText, byte[] salt);

    byte[] generateSalt();
}
