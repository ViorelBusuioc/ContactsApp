module dev.lpa.contactsapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.lpa.contactsapp to javafx.fxml;
    exports dev.lpa.contactsapp;
}