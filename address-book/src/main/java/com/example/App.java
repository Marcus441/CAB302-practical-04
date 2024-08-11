package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {
  public static final String TITLE = "Address Book";   
  public static final int WIDTH= 640;
  public static final int HEIGHT= 360;   
  
  @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader= new FXMLLoader(App.class.getResource("hello-view.fxml"));  
        Scene scene = new Scene(fxmlLoader, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle(TITLE); 
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
