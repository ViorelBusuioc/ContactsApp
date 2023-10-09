package dev.lpa.contactsapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddNewContactController {

    @FXML
    TextField firstNameInput;
    @FXML
    TextField lastNameInput;
    @FXML
    TextField phoneNumberInput;
    @FXML
    TextArea notesInput;



    public Contact processResult() {

        String firstNameString = firstNameInput.getText().trim();
        String lastNameString = lastNameInput.getText().trim();
        String phoneNumberString = phoneNumberInput.getText().trim();
        String notesString = notesInput.getText().trim();

        return new Contact(firstNameString, lastNameString, phoneNumberString, notesString);
    }

    public AddNewContactController() {
    }

    public AddNewContactController(TextField firstNameInput, TextField lastNameInput, TextField phoneNumberInput, TextArea notesInput) {
        this.firstNameInput = firstNameInput;
        this.lastNameInput = lastNameInput;
        this.phoneNumberInput = phoneNumberInput;
        this.notesInput = notesInput;
    }

    public void populateFields(Contact contact) {

        firstNameInput.setText(contact.getFirstName());
        lastNameInput.setText(contact.getLastName());
        phoneNumberInput.setText(contact.getPhoneNumber());
        notesInput.setText(contact.getNotes());
    }

    public void updateContact(Contact contact) {

        String firstNameString = firstNameInput.getText().trim();
        String lastNameString = lastNameInput.getText().trim();
        String phoneNumberString = phoneNumberInput.getText().trim();
        String notesString = notesInput.getText().trim();

        contact.setFirstName(firstNameString);
        contact.setLastName(lastNameString);
        contact.setPhoneNumber(phoneNumberString);
        contact.setNotes(notesString);

    }

    public TextField getFirstNameInput() {
        return firstNameInput;
    }

    public void setFirstNameInput(TextField firstNameInput) {
        this.firstNameInput = firstNameInput;
    }

    public TextField getLastNameInput() {
        return lastNameInput;
    }

    public void setLastNameInput(TextField lastNameInput) {
        this.lastNameInput = lastNameInput;
    }

    public TextField getPhoneNumberInput() {
        return phoneNumberInput;
    }

    public void setPhoneNumberInput(TextField phoneNumberInput) {
        this.phoneNumberInput = phoneNumberInput;
    }

    public TextArea getNotesInput() {
        return notesInput;
    }

    public void setNotesInput(TextArea notesInput) {
        this.notesInput = notesInput;
    }
}

