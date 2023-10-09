package dev.lpa.contactsapp;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private GridPane mainWindow;
    @FXML
    private MenuItem addContact;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextField searchText;
    @FXML
    private TableView<Contact> tableView;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;
    @FXML
    private TableColumn<Contact, String> phoneNumberColumn;
    @FXML
    private TableColumn<Contact, String> notesColumn;
    private FilteredList<Contact> filteredList;
    private ContactManager contactManager = new ContactManager();


    public void initialize() {

        contactManager.load();

        ObservableList<Contact> imports = contactManager.getContactsToSave();

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("notes"));

        filteredList = new FilteredList<>(imports);

        tableView.setItems(filteredList);

    }

    public void handleAddButton() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        dialog.setHeaderText("Use this windows to insert new Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addNewContactWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddNewContactController controller = fxmlLoader.getController();
            Contact contact = controller.processResult();
            contactManager.addContact(contact);
            tableView.refresh();
        }

    }

    public void handleDeleteButton() {

        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Contact");
            alert.setHeaderText("Delete Contact: " + tableView.getSelectionModel().getSelectedItem());
            alert.setContentText("Press OK to confirm, Cancel to return");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                contactManager.removeContact(selectedContact);
                tableView.getItems().remove(selectedContact);
            }
        }
    }

    public void handleClose() {
        Platform.exit();
    }

    public void handleSave() throws IOException {
        try {
            contactManager.store();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Contacts Saved");
    }

    public void handleEditButton() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            showEditDialog(selectedContact);
        }
    }

    private void showEditDialog(Contact contact) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        dialog.setHeaderText("Edit contact information");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addNewContactWindow.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            AddNewContactController controller = fxmlLoader.getController();
            controller.populateFields(contact);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddNewContactController controller = fxmlLoader.getController();
            controller.updateContact(contact);
            tableView.refresh();
        }
    }

    public void handleAbout() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Application coded by Viorel Busuioc");
        alert.setContentText("Nu stiu daca ai vazut din prima, dar mai scriu odata, CODE BY VIOREL BUSUIOC");

        alert.showAndWait();
    }


    public void handleSearchButton() {

        String searchQuery = searchText.getText().toLowerCase();

        filteredList.setPredicate(contact ->
                contact.getFirstName().toLowerCase().contains(searchQuery) ||
                        contact.getLastName().toLowerCase().contains(searchQuery) ||
                        contact.getPhoneNumber().toLowerCase().contains(searchQuery) ||
                        contact.getNotes().toLowerCase().contains(searchQuery)
        );

    }

    public void handleClearButton() {

        searchText.setText("");
        filteredList.setPredicate(contact -> true);
    }

}
