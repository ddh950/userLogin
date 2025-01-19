package de.userlogin.rs;

import de.userlogin.persistence.UserDAO;
import de.userlogin.persistence.UserEntity;
import de.userlogin.services.AuthService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class UserService {


    @Inject
    UserDAO userDAO;

    @Inject
    AuthService authService;

    public List<UserDTO> findAll() {
        List<UserEntity> allUsers = userDAO.findAll();

        List<UserDTO> result = new LinkedList<>();

        for(UserEntity user: allUsers) {

            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setMailAdress(user.getMailAdress());

            result.add(userDTO);

        }

        return result;
    }

    public void createUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDTO.getId());
        userEntity.setMailAdress(userDTO.getMailAdress());

        byte[] salt = authService.generateSalt();
        userEntity.setSalt(salt);
        byte[] encryptedPassword = authService.encrypt(userDTO.getPasswordPlainText().toCharArray(), salt);
        userEntity.setEncryptedPassword(encryptedPassword);

        userDAO.persist(userEntity);
    }
}
