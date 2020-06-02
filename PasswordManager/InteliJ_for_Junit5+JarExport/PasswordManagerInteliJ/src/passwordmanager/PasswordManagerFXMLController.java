/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static passwordmanager.PasswordManager.accounts;

/**
 * FXML Controller class
 *
 * @author P225365
 */
public class PasswordManagerFXMLController implements Initializable {

    //////////
    //LinkedList
    @FXML
    public  ComboBox<String> comboBox ;


    @FXML
    private TextField textfieldAccount;
    @FXML
    private TextField textfieldUser;
    @FXML
    private TextField textfieldPassword;
    @FXML
    public  TableView<Account> tableview;
    @FXML
    private TableColumn<Account, String> accountCol = new TableColumn<>("Account");
    @FXML
    private TableColumn<Account, String> userCol = new TableColumn<>("User/Email");
    @FXML
    private TableColumn<Account, String> passCol = new TableColumn<>("Password");
    @FXML
    private TextField searchtextfield;
    String searchtype = "";
    public static HashEncryption crypt;
    public MyFunctions function;


    /**
     * Initializes the controller class.
     */
    
    /*supplementary methods:
    MyFunctions: --- function CLASS
    saveFile()
    readFile()
    mergeSort()
    displayAccounts()
    searchLinkedList(bool delete)
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        ObservableList<String> options = FXCollections.observableArrayList("Account", "Email/Username", "Password");

        comboBox.setItems(options);
        //setcellfactory for tableview and account object
        accountCol.setCellValueFactory(
                new PropertyValueFactory<Account, String>("account"));

        userCol.setCellValueFactory(
                new PropertyValueFactory<Account, String>("userEmail"));
        passCol.setCellValueFactory(
                new PropertyValueFactory<Account, String>("password"));

        try {
            function = new MyFunctions();
            crypt = new HashEncryption();
            function.readFile();

            function.displayAccounts(tableview);
        } catch (Exception ex) {
            Logger.getLogger(PasswordManagerFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void searchClick(MouseEvent event) {
        searchLinkedList(searchtextfield.getText(), false);

    }

    @FXML
    private void deleteCLick(MouseEvent event) {
        searchLinkedList(searchtextfield.getText(), true);
        function.saveFile();
    }


    @FXML
    private void saveClick(MouseEvent event) throws NoSuchAlgorithmException{
        /*Save = concatenate textfields with unique separator->
        add to LinkedList-> Mergesort -> clear listview -> populate listview*/
        Account nuuacc = new Account(textfieldAccount.getText(), textfieldUser.getText(), textfieldPassword.getText());
        PasswordManager.accounts.add(nuuacc);
        //Collections.sort(accounts, new Account()); //also available comparable sort with Collections
        function.mergeSort(PasswordManager.accounts, PasswordManager.accounts.size());
        function.saveFile();
        function.displayAccounts(tableview);

    }

    @FXML
    private void helpClick(MouseEvent event) throws IOException {
        //using 
        File help = new File("C:/Secrets/help.html");
        if (help.exists()) {
            try {
                Desktop.getDesktop().browse(help.toURI());
            } catch (Exception ex) {
                System.out.println("it exists but cant open");
            }
        } else {
            //create then browse
            if (help.createNewFile()) {//if file isnt there make it
                System.out.println("Help created!");
            }
            FileWriter creator = new FileWriter(help, true);
            PrintWriter writer = new PrintWriter(creator, true);
            writer.printf("%s" + "%n", "<h1>Password Manager HELP FILE</h1>");
            writer.printf("%s" + "%n", "<h1>USAGE WARNING</h1>");
            writer.printf("%s" + "%n", "<p>Encrypted via AES, could be blowfish or even a one-time pad, however this method of storing(application) is not secure for important credentials</p>");
            writer.printf("%s" + "%n", "<p>I take no responsibility for any damages caused by the abuse of this application</p>");
            writer.printf("%s" + "%n", "<p>If you are not convinced of the security danger please read this leisurely article of acoustic cryptanalysis carried out by one of the inventors of RSA:"
                    + " <a href = \"https://www.extremetech.com/extreme/173108-researchers-crack-the-worlds-toughest-encryption-by-listening-to-the-tiny-sounds-made-by-your-computers-cpu\">Researchers"
                    + " crack the world’s toughest encryption by listening to the tiny sounds made by your computer’s CPU</a></p>");
            writer.printf("%s" + "%n", "<h2>Password Manager Functionality</h2>");
            writer.printf("%s" + "%n", "<p>This application can allow you to save accounts and their relevant details in an encrypted format locally to your desktop.</p>");
            writer.printf("%s" + "%n", "<p>To add an account fill in the GREEN textboxes at the top under \"New Accounts\"</p>");
            writer.printf("%s" + "%n", "<p>Click Save to encrypt and store the item to C:\\Secrets\\secrets.txt, this will also display your decrypted credentials on screen.</p>");
            writer.printf("%s" + "%n", "<p>Every time you open the application from now on the encrypted data in C:\\Secrets\\secrets.txt is decrypted and displayed in the bottom table.</p>");
            writer.printf("%s" + "%n", "<h3>Searching and Deleting</h3>");
            writer.printf("%s" + "%n", "<p>To search the list or delete an item from the list select the category you are searching under the BLUE \"Existing Accounts\" text, then proceed to type the correlated query</p>");
            writer.printf("%s" + "%n", "<p>if deleting you will be prompted with a confirmation message before deletion, if searching the item will be highlighted within the table in bright RED</p>");
            writer.close();
            creator.close();

            try {
                Desktop.getDesktop().browse(help.toURI());
            } catch (Exception ex) {
                System.out.println("it exists but cant open");
            }
        }

    }
    public void searchLinkedList(String searchstring, boolean delete) {
        searchtype = comboBox.getValue();
        if (searchstring.compareTo("") == 0) {
            JOptionPane.showMessageDialog(null,
                    "Please fill textbox above button!!!",
                    "EMPTY TEXT!!!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String prompt = "";
        if (delete) {
            prompt = "Delete Failed";
        } else {
            prompt = "Search Failed";
        }
        boolean found = false;
        boolean nocategory = false;
        if (searchtype == null) {
            searchtype = ""; //default switch case doesnt like null

        }
        try {

            for (Account acc : accounts) {
                if (found || nocategory) {
                    break;
                }

                switch (searchtype) {

                    case "Account":
                        if (searchstring.compareTo(acc.getAccount()) == 0) {
                            found = true;
                            if (!delete) {
                                int index = accounts.indexOf(acc);
                                tableview.requestFocus();
                                tableview.getSelectionModel().select(acc);
                                tableview.getSelectionModel().focus(index);
                            }

                            if (delete) {
                                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the following item?" + "\n" + acc.getLongID());
                                if (confirmation == 0) {
                                    accounts.remove(acc);
                                    function.displayAccounts(tableview);
                                    JOptionPane.showMessageDialog(null,
                                            acc.getLongID() + " " + "removed.",
                                            acc.getAccount() + " removed!",
                                            JOptionPane.OK_OPTION);
                                } else {

                                    JOptionPane.showMessageDialog(null,
                                            "Canceled removal.",
                                            acc.getAccount() + "Canceled delete.",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                                break;
                            }
                        }
                        break;

                    case "Email/Username":
                        if (searchstring.compareTo(acc.getUserEmail()) == 0) {
                            found = true;
                            if (!delete) {
                                int index = accounts.indexOf(acc);
                                tableview.requestFocus();
                                tableview.getSelectionModel().select(acc);
                                tableview.getSelectionModel().focus(index);
                            }

                            if (delete) {
                                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the following item?" + "\n" + acc.getLongID());
                                if (confirmation == 0) {
                                    accounts.remove(acc);
                                    function.displayAccounts(tableview);
                                    JOptionPane.showMessageDialog(null,
                                            acc.getLongID() + " " + "removed.",
                                            acc.getAccount() + " removed!",
                                            JOptionPane.OK_OPTION);
                                } else {

                                    JOptionPane.showMessageDialog(null,
                                            "Canceled removal.",
                                            acc.getAccount() + "Canceled delete.",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                                break;
                            }
                        }
                        break;
                    case "Password":
                        if (searchstring.compareTo(acc.getPassword()) == 0) {
                            found = true;
                            if (!delete) {
                                int index = accounts.indexOf(acc);
                                tableview.requestFocus();
                                tableview.getSelectionModel().select(acc);
                                tableview.getSelectionModel().focus(index);
                            }

                            if (delete) {
                                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the following item?" + "\n" + acc.getLongID());
                                if (confirmation == 0) {
                                    accounts.remove(acc);
                                    function.displayAccounts(tableview);
                                    JOptionPane.showMessageDialog(null,
                                            acc.getLongID() + " " + "removed.",
                                            acc.getAccount() + " removed!",
                                            JOptionPane.OK_OPTION);
                                } else {

                                    JOptionPane.showMessageDialog(null,
                                            "Canceled removal.",
                                            acc.getAccount() + "Canceled delete.",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                                break;
                            }
                        }
                        break;
                    default:

                        JOptionPane.showMessageDialog(null,
                                "Please select which to category to look in.",
                                prompt,
                                JOptionPane.WARNING_MESSAGE);

                        nocategory = true;
                        break;
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException due to removing while in for each loop. dont stress still working.");
        }
        if (!found && !nocategory) {
            JOptionPane.showMessageDialog(null,
                    "What you are looking for doesn't exist.",
                    prompt,
                    JOptionPane.WARNING_MESSAGE);
        }

    }



}