module PasswordManagerInteliJ {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.swing;
    requires javafx.swt;
    requires java.logging;
    requires org.junit.jupiter.api;
    requires org.bouncycastle.provider;

    opens passwordmanager;
}