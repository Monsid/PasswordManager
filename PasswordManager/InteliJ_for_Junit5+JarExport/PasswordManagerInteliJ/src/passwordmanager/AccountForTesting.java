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
 * THIS OBJECT SIMPLY HAS HASH ENCRYPTION INSTANTIATED IN THIS CLASS INSTEAD ON FXMLCONTROLLER
 *
 * @author P225365 and references included
 */
public class AccountForTesting implements Comparable<AccountForTesting>, Comparator<AccountForTesting> {

    //encryption/decryption interface for reading/writing to file
    private SimpleStringProperty account; //SimpleStringProperties are required for Tableview.setcellfactory(object) method
    private SimpleStringProperty userEmail;
    private SimpleStringProperty password;//clear password for displaying in application
    HashEncryption crypt = new HashEncryption();

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    String passhash;//hashed password for writing to file

    AccountForTesting() throws Exception {
    }

    public String getLongID() {
        return this.getAccount() + " " + this.getUserEmail() + " " + this.getPassword();
    }

    AccountForTesting(String account, String userEmail, String password) throws Exception {
        this.account = new SimpleStringProperty(account);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.password = new SimpleStringProperty(password);

        passhash = crypt.encrypt(password);//instatiate as String

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

        return crypt.encrypt(this.getPassword());
    }

    String passReverseHash(String hash) throws InvalidKeyException {

        //passReverserHash gets the original readible password
        return crypt.decrypt(hash);

    }

    @Override
    public int compareTo(AccountForTesting other) {
        return this.getAccount().compareTo(other.getAccount());
    }

    @Override
    public int compare(AccountForTesting o1, AccountForTesting o2) {
        if (o1.getAccount().compareTo(o2.getAccount()) < 0) {
            return -1;
        } else if (o1.getAccount().compareTo(o2.getAccount()) == 0) {
            return 0;
        } else {
            return +1;
        }

    }
}