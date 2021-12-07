package application;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	@FXML
	Label onSave;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Window.fxml"));
			Scene scene = new Scene(root,500,400);
			scene.getStylesheets().add(getClass().getResource("Window.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("MySpy");
			primaryStage.show();
			Label saveData = (Label) scene.lookup("#saveData");
			
			//Adds a confirmation close dialog
			primaryStage.setOnCloseRequest(evt -> {
			    Alert alert = new Alert(AlertType.CONFIRMATION);
			    alert.setTitle("Confirm Close");
			    alert.setHeaderText("Are you sure you want to close the program?");
			    alert.showAndWait().filter(r -> r != ButtonType.OK).ifPresent(r->evt.consume());
			});		
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}

	
	public static void main(String[] args) {
		launch(args);
	}
	
}