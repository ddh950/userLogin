package de.userlogin.persistence;

import javax.persistence.*;


// As a JPA Entity, it can be stored in the database.
 
@Entity
public class UserEntity {

    public static final String COLUMN_MAIL_ADRESS = "mailAdress";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = COLUMN_MAIL_ADRESS, unique = true)
    private String mailAdress;

    private byte[] encryptedPassword;
    private byte[] salt;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMailAdress() {
        return mailAdress;
    }

    public void setMailAdress(String mailAdresse) {
        this.mailAdress = mailAdresse;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] password) {
        this.encryptedPassword = password;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }
}
