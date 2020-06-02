package passwordmanager;

import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    AccountForTesting testaccount;

    @BeforeEach
    void setUp() throws Exception {
        testaccount = new AccountForTesting("testaccount", "useremail", "password123");
    }

    @Test
    void createAccount() {
        String actual = testaccount.getLongID();
        String expected = "testaccount useremail password123";
        try {
            assertEquals(expected, actual);
            System.out.println("Test: createAccount()\nSuccessfully created object.");
        } catch (Exception e) {
            fail();
            System.out.println("Test: createAccount()\nTest Failed");
        }
    }

    @Test
    void passwordHashing() throws NoSuchAlgorithmException {

        String actual = testaccount.passHasher();
        String notexpected = testaccount.getPassword();
        try {
            assertNotEquals(notexpected, actual);
            System.out.println("Test: passwordHashing()\nSuccessfully hashed password - " + notexpected + " : " + actual);
        } catch (Exception e) {
            fail();
            System.out.println("Test: passwordHashing()\nTest Failed");
        }
    }

    @Test
    void passwordDecrypting() throws NoSuchAlgorithmException, InvalidKeyException {
        testaccount.setPassword(new SimpleStringProperty("newsecret"));
        testaccount.setPasshash(testaccount.passHasher());
        String actual = testaccount.passReverseHash(testaccount.getPasshash());
        String expected = "newsecret";
        try {
            assertEquals(expected, actual);
            System.out.println("Test: passwordDecrypting()\nSuccessfully decrypted password - " + expected + " : " + actual);
        } catch (Exception e) {
            fail();
            System.out.println("Test: passwordDecrypting()\nTest Failed");
        }
    }

}