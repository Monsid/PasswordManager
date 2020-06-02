package passwordmanager;


import javafx.beans.property.SimpleStringProperty;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * i removed display() from effect and removed references and inserted them here in order to test locally
 * also mofified file name for writing
 *
 * @author P225365
 */
public class MyFunctionsForTesting {

    static LinkedList<AccountForTesting> accounts = new LinkedList<>();

    public void saveFile() {
        //write file
        //for each account write totalcount at the top line
        //for each of 3 variables ( account, username, passhash ) writeline in file

        //passwords folder "C:\Secrets\mysecrets.txt" does not exist then create one
        File secretdir;
        try {
            secretdir = new File("C:/Secrets");
            if (!secretdir.exists()) {
                if (secretdir.mkdir()) {
                    secretdir = new File(secretdir + "/mysecretstest.txt");
                }//if folder isnt there make it
                if (secretdir.createNewFile()) {//if file isnt there make it
                    System.out.println("Not there so made it");
                } else {
                    System.out.println("couldnt make it");
                }
            } else {
                secretdir = new File(secretdir + "/mysecretstest.txt");
                if (secretdir.delete()) {
                    System.out.println("file deleted for rewrite");
                }
                {
                    System.out.println("File deleted successfully");
                }
            }
            //now write all existing objects to file
            FileWriter creator = new FileWriter(secretdir, true);
            PrintWriter writer = new PrintWriter(creator, true);
            writer.printf("%s" + "%n", accounts.size());
            writer.printf("%s" + "%n", "");//had to calibrate with empty due to checking first taking 2 strings?

            for (AccountForTesting acc : accounts) {
                writer.printf("%s" + "%n", acc.getAccount());
                writer.printf("%s" + "%n", acc.getUserEmail());
                writer.print(acc.getPasshash() + "\n"); //SAVING AS BYTE because as string it produces ascii char of \n and i cant catch it with replaceall(\n, \\n)
            }
            writer.close();
            creator.close();

        } catch (IOException ex) {
            Logger.getLogger(PasswordManagerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

    }

    public void readFile() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        //read top line => assign to int count
        //while reading nextline, assign line to object variables in same order as saveFile
        //in same block assign Simplepropertystring password of object to Decrypt(key, string.asbyte())
        File secretdir = new File("C:/Secrets/mysecretstest.txt");
        if (secretdir.exists()) {

            try {
                File file = new File("C:/Secrets/mysecretstest.txt");    //creates a new file instance
                FileReader fr = new FileReader(file);   //reads the file
                BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
                String line;

                int count = Integer.parseInt(br.readLine());
                for (int i = 0; i < count; i++) {
                    AccountForTesting nuacc = new AccountForTesting();
                    accounts.add(nuacc);

                }

                while ((line = br.readLine()) != null) {

                    for (AccountForTesting acc : accounts) {
                        acc.setAccount(new SimpleStringProperty(br.readLine()));
                        acc.setUserEmail(new SimpleStringProperty(br.readLine()));

                        //acc.setPasshash(br.readLine());//set passhash for file saves
                        String hash = br.readLine();
                        acc.setPasshash(hash);
                        acc.setPassword(new SimpleStringProperty((acc.passReverseHash(hash))));//from string hash dehash

                    }
                }
                fr.close();    //closes the stream and release the resources

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void mergeSort(LinkedList<AccountForTesting> accounts, int size) {

        //convert to basic array type
        Comparable[] mainarray = new AccountForTesting[size];

        for (int a = 0; a < mainarray.length; a++) {
            mainarray[a] = accounts.get(a);
        }
        //send to recurssive method
        divide(mainarray, size);
        accounts.clear();
        for (Comparable accs : mainarray) {
            accounts.add((AccountForTesting) accs);
        }

    }

    public Comparable[] divide(Comparable[] mainarray, int size) {

        if (size < 2) {
            return mainarray;
        }
        int mid = size / 2;
        Comparable[] subarrLeft = new Comparable[mid];
        Comparable[] subarrRight = new Comparable[size - mid];
        //split the list in half with two sublists
        for (int i = 0; i < mid; i++) {
            subarrLeft[i] = mainarray[i];
        }
        for (int i = mid; i < size; i++) {
            subarrRight[i - mid] = mainarray[i];
        }
        divide(subarrLeft, mid);
        divide(subarrRight, size - mid);

        conquer(mainarray, subarrLeft, subarrRight, mid, (size - mid));
        return mainarray;

    }

    public void conquer(Comparable[] mainarray, Comparable[] subarrleft, Comparable[] subarrright, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            // using default comparable comparator
            if (subarrleft[i].compareTo(subarrright[j]) < 0) {
                mainarray[k++] = subarrleft[i++];
            } else {
                mainarray[k++] = subarrright[j++];
            }
        }
        while (i < left) {
            mainarray[k++] = subarrleft[i++];
        }
        while (j < right) {
            mainarray[k++] = subarrright[j++];
        }
    }
/*
    public void displayAccounts(TableView<Account> tableview) {
        //Accounts section

        ObservableList<AccountForTesting> rows = FXCollections.observableArrayList();
        for (int i = 0; i < accounts.size(); i++) {
            rows.add(accounts.get(i));
        }
        tableview.setItems(rows);

    }
*/

}
