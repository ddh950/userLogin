package de.userlogin.persistence;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import de.userlogin.services.AuthService;

import java.security.SecureRandom;

//on startup test data generated -> setupTestData method
@Singleton
@Startup
public class TestDataGenerator {

    @Inject
    UserDAO userDAO;

    @Inject
    AuthService authService;

    @PostConstruct
    public void setupTestData() {
        UserEntity user1 = new UserEntity();
        user1.setMailAdress("teddy.bear@test.com");
        byte[] salt1 = authService.generateSalt();
        user1.setSalt(salt1);
        user1.setEncryptedPassword(authService.encrypt("1234".toCharArray(), salt1));
        userDAO.persist(user1);


        UserEntity user2 = new UserEntity();
        user2.setMailAdress("s.stallone@test.com");
        byte[] salt2 = authService.generateSalt();
        user2.setSalt(salt2);
        user2.setEncryptedPassword(authService.encrypt("1234".toCharArray(), salt2));
        userDAO.persist(user2);

    }



}
