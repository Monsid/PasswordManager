/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordmanager;

import javafx.beans.property.SimpleStringProperty;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;

/**
 * @author P225365 and references included
 */
public class Account implements Comparable<Account>, Comparator<Account> {

    //encryption/decryption interface for reading/writing to file
    private SimpleStringProperty account; //SimpleStringProperties are required for Tableview.setcellfactory(object) method
    private SimpleStringProperty userEmail;
    private SimpleStringProperty password;//clear password for displaying in application

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    String passhash;//hashed password for writing to file

    Account() {
    }

    public String getLongID() {
        return this.getAccount() + " " + this.getUserEmail() + " " + this.getPassword();
    }

    Account(String account, String userEmail, String password)  {
        this.account = new SimpleStringProperty(account);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.password = new SimpleStringProperty(password);

        passhash = PasswordManagerFXMLController.crypt.encrypt(password);//instatiate as String

    }

    public String getPasshash() {
        return passhash;
    }

    public String getAccount() {
        return account.getValue();
    }

    public void setAccount(SimpleStringProperty account) {
        this.account = account;
    }

    public String getUserEmail() {
        return userEmail.getValue();
    }

    public void setUserEmail(SimpleStringProperty userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setPassword(SimpleStringProperty password) {
        this.password = password;
    }

    String passHasher() throws NoSuchAlgorithmException { //pass hasher encrypts passwords for the object constructor using Boyncy castle hex endecoder.

        return PasswordManagerFXMLController.crypt.encrypt(this.getPassword());
    }

    String passReverseHash(String hash) throws InvalidKeyException {

        //passReverserHash gets the original readible password
        return PasswordManagerFXMLController.crypt.decrypt(hash);

    }

    @Override
    public int compareTo(Account other) {
        return this.getAccount().compareTo(other.getAccount());
    }

    @Override
    public int compare(Account o1, Account o2) {
        if (o1.getAccount().compareTo(o2.getAccount()) < 0) {
            return -1;
        } else if (o1.getAccount().compareTo(o2.getAccount()) == 0) {
            return 0;
        } else {
            return +1;
        }

    }
}