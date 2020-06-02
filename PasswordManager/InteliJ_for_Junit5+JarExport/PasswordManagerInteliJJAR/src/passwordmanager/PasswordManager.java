
package passwordmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/**
 *
 * @author P225365
 */
public class PasswordManager extends Application {
    //if crypt exists as file then open instead of creating


    static LinkedList<Account> accounts = new LinkedList();

    @Override
    public void start(Stage stage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("PasswordManagerFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {


        launch(args);

    }

}