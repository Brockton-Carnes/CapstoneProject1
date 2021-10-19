package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class WindowController {
	
	@FXML
	private Label FileLabel;

	@FXML
	private Scene scene;
	
	@FXML
	public void fileOpen (MouseEvent event) {
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		System.out.println(selectedFile.getAbsolutePath());
	}
	@FXML
	public void showData (MouseEvent event) throws IOException {		 
		scene = FileLabel.getScene();
		DatePicker initialDatePicker = (DatePicker) scene.lookup("#initialDate");
        LocalDate dateStart = initialDatePicker.getValue();
		DatePicker endDatePicker = (DatePicker) scene.lookup("#endDate");
        LocalDate dateEnd = endDatePicker.getValue();
        TextArea outputDisplay = (TextArea) scene.lookup("#outputDisplay");        
        
        ArrayList<String> filesInDir = getFilesInDirectory("C:\\Users\\brock\\Desktop\\CapstoneTestData");
        outputDisplay.clear();
        for(String s: filesInDir) {
        	LocalDate temp = LocalDate.of(Integer.parseInt(s.split("-")[0]),Integer.parseInt(s.split("-")[1]), Integer.parseInt(s.split("-")[2]));
        	if (temp.isAfter(dateStart.minusDays(1)) && temp.isBefore(dateEnd.plusDays(1))) {
            	outputDisplay.appendText(s + "\n");
        		outputDisplay.appendText(getFileContentsByStringDate("C:\\Users\\brock\\Desktop\\CapstoneTestData\\" + s + ".txt"));
        	}
        }
	}
	
	ArrayList<String> getFilesInDirectory(String directory){
		ArrayList<String> output = new ArrayList<String>();
		File directoryOfData = new File(directory);
		for(File file: directoryOfData.listFiles()) {
			output.add(file.getName().substring(0,file.getName().length()-4));
		}
		return output;
	}
	
	private String getFileContentsByStringDate(String yearMonthDayDirectory) throws IOException {
		FileInputStream fileToFind = new FileInputStream(yearMonthDayDirectory);
		Scanner fs = new Scanner(fileToFind);
		String output = "";
		while(fs.hasNextLine()) {
			output += fs.nextLine() + "\n";
		}
		fs.close();
		fileToFind.close();
		return output;
	}
	
}
 