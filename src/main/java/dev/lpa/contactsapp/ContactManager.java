package dev.lpa.contactsapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ContactManager {

    private ObservableList<Contact> contactsToSave = FXCollections.observableArrayList();
    private static String saveFile = "saveFile.txt";

    public ContactManager() {

    }

    public ObservableList<Contact> getContactsToSave() {
        return contactsToSave;
    }

    public void setContactsToSave(ObservableList<Contact> contactsToSave) {
        this.contactsToSave = contactsToSave;
    }


    public void addContact(String first, String last, String phone, String note) {
        contactsToSave.add(new Contact(first, last, phone, note));
    }

    public void addContact(Contact contact) {
        contactsToSave.add(contact);
    }

    public void store () throws IOException {

        Path path = Paths.get(saveFile);
        BufferedWriter writer = Files.newBufferedWriter(path);

        try {
            for (Contact contact : contactsToSave) {
                writer.write(String.format("%s,%s,%s,%s",
                        contact.getFirstName(),
                        contact.getLastName(),
                        contact.getPhoneNumber(),
                        contact.getNotes()));
                writer.newLine();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public ObservableList<Contact> load() {

        Path path = Paths.get(saveFile);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String phoneNumber = parts[2];
                    String notes = parts[3];
                    Contact contact = new Contact(firstName, lastName, phoneNumber, notes);
                    contactsToSave.add(contact);
                    System.out.println("Contact: " + contact + " loaded!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactsToSave;
    }

    public void removeContact(Contact contact) {
        contactsToSave.remove(contact);
    }

}
