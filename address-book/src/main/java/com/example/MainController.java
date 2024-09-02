package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

/** MainController */
public class MainController {
  @FXML
  private ListView<Contact> contactsListView;
  private IContactDAO contactDAO;

  public MainController() {
    contactDAO = new MockContactDAO();
  }

  private ListCell<Contact> renderCell(ListView<Contact> contactListView) {
    return new ListCell<>() {
      @Override
      protected void updateItem(Contact contact, boolean empty) {
        super.updateItem(contact, empty);
        if (empty || contact == null || contact.getFullName() == null) {
          setText(null);
        } else {
          setText(contact.getFullName());
        }
      }
    };
  }

  private void syncContacts() {
    contactsListView.getItems().clear();
    contactsListView.getItems().addAll(contactDAO.getAllContacts());
  }

  @FXML
  public void initialize() {
    contactsListView.setCellFactory(this::renderCell);
    syncContacts();
  }
}
