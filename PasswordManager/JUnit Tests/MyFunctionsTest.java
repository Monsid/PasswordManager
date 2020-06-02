package passwordmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MyFunctionsTest {
    MyFunctionsForTesting functions;

    @BeforeEach
    void setUp() {
        functions = new MyFunctionsForTesting();
    }

    @Test
    void FileSaves() throws Exception {
        //create a few accounts and add to linked list//then save to file, read from file//assert equals with different list with same variables

        AccountForTesting acc1 = new AccountForTesting("1", "email", "pass1");
        AccountForTesting acc2 = new AccountForTesting("2", "email", "pass2");
        AccountForTesting acc3 = new AccountForTesting("3", "email", "pass3");
        LinkedList<AccountForTesting> expected = new LinkedList<>();
        expected.add(acc1);
        expected.add(acc2);
        expected.add(acc3);
        MyFunctionsForTesting.accounts.add(acc1);
        MyFunctionsForTesting.accounts.add(acc2);
        MyFunctionsForTesting.accounts.add(acc3);
        functions.saveFile();
        MyFunctionsForTesting.accounts.clear();//delete so to read object variables from file
        functions.readFile();
        LinkedList<AccountForTesting> actual = MyFunctionsForTesting.accounts;
        try {

            assertEquals(expected.get(0).getLongID(), actual.get(0).getLongID());
            assertEquals(expected.get(1).getLongID(), actual.get(1).getLongID());
            assertEquals(expected.get(2).getLongID(), actual.get(2).getLongID());
            System.out.println("Test FileSaves(): Successful, test objects created to file location C:\\Secrets\\secretstest.txt");
        } catch (Exception e) {
            fail();
            System.out.println("Test FileSaves(): Test Failed.");
        }
    }

    @Test
    void mergeSort() throws Exception {
        //add a bunch of random numbers in reverse order in Account variable of LinkedList of Accounts, Merge Sort, assertEquals
        AccountForTesting acc1 = new AccountForTesting("9", "email", "pass1");
        AccountForTesting acc2 = new AccountForTesting("6", "email", "pass2");
        AccountForTesting acc3 = new AccountForTesting("3", "email", "pass3");
        LinkedList<AccountForTesting> actual = new LinkedList<>();
        actual.addFirst(acc1);
        actual.add(acc2);
        actual.addLast(acc3);
        functions.mergeSort(actual, actual.size());

        AccountForTesting accone = new AccountForTesting("9", "email", "pass1");
        AccountForTesting acctwo = new AccountForTesting("6", "email", "pass2");
        AccountForTesting accthree = new AccountForTesting("3", "email", "pass3");
        LinkedList<AccountForTesting> expected = new LinkedList<>();
        expected.add(acctwo);
        expected.addFirst(accthree);expected.addLast(accone);

        try{
        assertEquals(expected.get(0).getLongID(), actual.get(0).getLongID());
        assertEquals(expected.get(1).getLongID(), actual.get(1).getLongID());
        assertEquals(expected.get(2).getLongID(), actual.get(2).getLongID());
            System.out.println("Test mergeSort(): Successful, test objects sorted by Account variable in incremental order");
        } catch (Exception e) {
            fail();
            System.out.println("Test mergeSort(): Test Failed.");
        }
    }
}