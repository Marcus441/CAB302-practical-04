package com.example;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

/** MainController */
public class MainController {
  @FXML
  private ListView<Contact> contactsListView;
  @FXML
  private TextField firstNameTextField;
  @FXML
  private TextField lastNameTextField;
  @FXML
  private TextField emailTextField;
  @FXML
  private TextField phoneTextField;
  @FXML
  private VBox contactContainer;

  private IContactDAO contactDAO;

  public MainController() {
    contactDAO = new MockContactDAO();
  }

  private void selectContact(Contact contact) {
    contactsListView.getSelectionModel().select(contact);
    firstNameTextField.setText(contact.getFirstName());
    lastNameTextField.setText(contact.getLastName());
    emailTextField.setText(contact.getEmail());
    phoneTextField.setText(contact.getPhone());
  }

  private ListCell<Contact> renderCell(ListView<Contact> contactListView) {

    return new ListCell<>() {
      private void onContactSelected(MouseEvent mouseEvent) {
        ListCell<Contact> clickedCell = (ListCell<Contact>) mouseEvent.getSource();
        // Get the selected contact from the list view
        Contact selectedContact = clickedCell.getItem();
        if (selectedContact != null)
          selectContact(selectedContact);
      }

      @Override
      protected void updateItem(Contact contact, boolean empty) {
        super.updateItem(contact, empty);
        // If the cell is empty, set the text to null, otherwise set it to the contact's
        // full name
        if (empty || contact == null || contact.getFullName() == null) {
          setText(null);
          super.setOnMouseClicked(this::onContactSelected);
        } else {
          setText(contact.getFullName());
        }
      }
    };
  }

  private void syncContacts() {
    contactsListView.getItems().clear();
    List<Contact> contacts = contactDAO.getAllContacts();
    boolean hasContact = !contacts.isEmpty();
    if (hasContact) {
      contactsListView.getItems().addAll(contacts);
    }
    // Show / hide based on whether there are contacts
    contactContainer.setVisible(hasContact);
  }

  @FXML
  public void initialize() {
    contactsListView.setCellFactory(this::renderCell);
    syncContacts();
    // Select the first contact and display its information
    contactsListView.getSelectionModel().selectFirst();
    Contact firstContact = contactsListView.getSelectionModel().getSelectedItem();
    if (firstContact != null) {
      selectContact(firstContact);
    }
  }

  @FXML
  private void onEditConfirm() {
    // Get the selected contact from the list view
    Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
    if (selectedContact != null) {
      selectedContact.setFirstName(firstNameTextField.getText());
      selectedContact.setLastName(lastNameTextField.getText());
      selectedContact.setEmail(emailTextField.getText());
      selectedContact.setPhone(phoneTextField.getText());
      contactDAO.updateContact(selectedContact);
      syncContacts();
    }
  }

  @FXML
  private void onAdd() {
    // Default values for a new contact
    final String DEFAULT_FIRST_NAME = "New";
    final String DEFAULT_LAST_NAME = "Contact";
    final String DEFAULT_EMAIL = "";
    final String DEFAULT_PHONE = "";
    Contact newContact = new Contact(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PHONE);
    // Add the new contact to the database
    contactDAO.addContact(newContact);
    syncContacts();
    // Select the new contact in the list view
    // and focus the first name text field
    selectContact(newContact);
    firstNameTextField.requestFocus();
  }

  @FXML
  private void onDelete() {
    // Get the selected contact from the list view
    Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
    if (selectedContact != null) {
      contactDAO.deleteContact(selectedContact);
      syncContacts();
    }
  }

  @FXML
  private void onCancel() {
    // Find the selected contact
    Contact selectedContact = contactsListView.getSelectionModel().getSelectedItem();
    if (selectedContact != null) {
      // Since the contact hasn't been modified,
      // we can just re-select it to refresh the text fields
      selectContact(selectedContact);
    }
  }

}
