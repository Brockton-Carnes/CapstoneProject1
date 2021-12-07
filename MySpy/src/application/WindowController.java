package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class WindowController {
	
	private final String PATH_TO_DATA_FOLDER = "C:\\Users\\brock\\Desktop\\CapstoneTestData\\";
	private final String DOTTXT = ".txt";
	private final String SEARCH_BAR_ID = "#searchBar";
	private final String OUTPUT_DISPLAY_ID = "#outputDisplay";
	private final String SEARCH_BUTTON_ID = "#search";
	private final String WORD_FREQ_ID = "#wordFrequency";
	private final String INITIAL_DATE = "#initialDate";
	private final String END_DATE = "#endDate";
	
	@FXML
	private Label FileLabel;

	@FXML
	private Scene scene;
	
	@FXML
	public void fileOpen (MouseEvent event) throws IOException {
		scene = FileLabel.getScene();
		TextArea outputDisplay = (TextArea) scene.lookup(OUTPUT_DISPLAY_ID);
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		if(selectedFile != null) 
			outputDisplay.setText(getFileContentsByStringDate(selectedFile.getAbsolutePath()));
		else 
			System.out.println("No File Selected");
	}
	
	@FXML
	public void saveData (MouseEvent event) throws IOException {
		scene = FileLabel.getScene();
		TextArea outputDisplay = (TextArea) scene.lookup(OUTPUT_DISPLAY_ID);
		FileChooser fc = new FileChooser();
		
		 //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fc.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fc.showSaveDialog(null);
        
        if(file != null){
            SaveFile(outputDisplay.getText(), file);
        }
        
	}
	
    private void SaveFile(String content, File file) throws IOException{
        FileWriter fileWriter;
        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();  
        }
         
    
	
	@FXML
	public void clearData (MouseEvent event) throws IOException {
		TextArea outputDisplay = (TextArea) scene.lookup(OUTPUT_DISPLAY_ID);
		DatePicker initialDatePicker = (DatePicker) scene.lookup(INITIAL_DATE);
		DatePicker endDatePicker = (DatePicker) scene.lookup(END_DATE);
		TextField bar = (TextField) scene.lookup(SEARCH_BAR_ID);
		outputDisplay.clear();
		initialDatePicker.getEditor().clear();
		endDatePicker.getEditor().clear();
		bar.clear();
	}
	
	
	@FXML
	public void searchData (MouseEvent event) throws IOException {
		scene = FileLabel.getScene();
		TextField bar = (TextField) scene.lookup(SEARCH_BAR_ID);
		Label searchButton = (Label) scene.lookup(SEARCH_BUTTON_ID);
		TextArea outputDisplay = (TextArea) scene.lookup(OUTPUT_DISPLAY_ID);

		String searchBarOutput = bar.getText();
		String outputDisplayText = outputDisplay.getText();
		
		DatePicker initialDatePicker = (DatePicker) scene.lookup(INITIAL_DATE);
        LocalDate dateStart = initialDatePicker.getValue();
		DatePicker endDatePicker = (DatePicker) scene.lookup(END_DATE);
        LocalDate dateEnd = endDatePicker.getValue();
        
        
        ArrayList<String> filesInDir = getFilesInDirectory(PATH_TO_DATA_FOLDER);
        outputDisplay.clear();
        for(String s: filesInDir) {
        	LocalDate temp = LocalDate.of(Integer.parseInt(s.split("-")[0]),Integer.parseInt(s.split("-")[1]), Integer.parseInt(s.split("-")[2]));
        	if (temp.isAfter(dateStart.minusDays(1)) && temp.isBefore(dateEnd.plusDays(1))) {
            	outputDisplay.appendText(s + "\n");
        		outputDisplay.appendText(getFileContentsByStringDateBySearchValue(PATH_TO_DATA_FOLDER + s + DOTTXT, searchBarOutput));
        	}
        }
		
	}
	
	@FXML
	public void wordFrequency (MouseEvent event) throws IOException {
		scene = FileLabel.getScene();
		DatePicker initialDatePicker = (DatePicker) scene.lookup(INITIAL_DATE);
        LocalDate dateStart = initialDatePicker.getValue();
		DatePicker endDatePicker = (DatePicker) scene.lookup(END_DATE);
        LocalDate dateEnd = endDatePicker.getValue();
        TextArea outputDisplay = (TextArea) scene.lookup(OUTPUT_DISPLAY_ID);
		String outputDisplayText = outputDisplay.getText();
		
		//Fixed bug for counting spaces when no data in text area
		if(outputDisplayText.isBlank()) {
			return;
		}

		String [] wordsSplit = outputDisplayText.split(" ");
	    int count = 0;
	    outputDisplay.clear();

	    //This will look at the first word
	    for (int i = 0; i < wordsSplit.length; i++) {
	        count = 0;
	 
	        //This will compare the first word to the rest of the words  
	        for (int j = 0; j < wordsSplit.length; j++) {
	            String temp = wordsSplit[j];
	            String temp1 = wordsSplit[i];
	  
	            if (j < i && temp.contentEquals(temp1)) {
	                break;
	            }
	            
	            if (temp.contentEquals(temp1)) {
	                count = count + 1;
	            }
	            
	            if (j == wordsSplit.length - 1) {
	            	outputDisplay.appendText(wordsSplit[i] + "          " + count + "\n");
	            }

	        }

	    }
	    
	}

	@FXML
	public void showData (MouseEvent event) throws IOException {		 
		scene = FileLabel.getScene();
		DatePicker initialDatePicker = (DatePicker) scene.lookup("#initialDate");
        LocalDate dateStart = initialDatePicker.getValue();
		DatePicker endDatePicker = (DatePicker) scene.lookup("#endDate");
        LocalDate dateEnd = endDatePicker.getValue();
        TextArea outputDisplay = (TextArea) scene.lookup(OUTPUT_DISPLAY_ID);        
        
        ArrayList<String> filesInDir = getFilesInDirectory(PATH_TO_DATA_FOLDER);
        outputDisplay.clear();
        for(String s: filesInDir) {
        	LocalDate temp = LocalDate.of(Integer.parseInt(s.split("-")[0]),Integer.parseInt(s.split("-")[1]), Integer.parseInt(s.split("-")[2]));
        	if (temp.isAfter(dateStart.minusDays(1)) && temp.isBefore(dateEnd.plusDays(1))) { //Inclusive
            	outputDisplay.appendText(s + "\n"); //Adding an extra line for visual purposes
        		outputDisplay.appendText(getFileContentsByStringDate(PATH_TO_DATA_FOLDER + s + DOTTXT));
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
	
	private String getFileContentsByStringDateBySearchValue(String yearMonthDayDirectory, String searchValue) throws IOException {
		LogData logdata = new LogData(yearMonthDayDirectory);
		String output = logdata.formattedDisplayDataBySearchValue(searchValue);
		return output;
	}
	
	private String getFileContentsByStringDateBySearchValue(String searchValue, ArrayList<LogData> logDataList) throws IOException {
		String output = "";
		for(LogData logData : logDataList) {
			output += logData.formattedDisplayDataBySearchValue(searchValue);
		}
		return output;
	}
	
	private String getFileContentsByStringDate(String yearMonthDayDirectory) throws IOException {
		LogData logdata = new LogData(yearMonthDayDirectory);
		String output = logdata.formattedDisplayData();
		return output;
	}
	
	
	
	private ArrayList <LogData> parseLogData (){
		scene = FileLabel.getScene();
		TextArea outputDisplay = (TextArea) scene.lookup(OUTPUT_DISPLAY_ID);
		ArrayList<LogData> output = new ArrayList <LogData>();
		String outPutDisplayText = outputDisplay.getText();
		System.out.print(outPutDisplayText);
		String indexOfDoubleNewLines = getIndexOfDoubleEmptyLines(outPutDisplayText).toString();
		System.out.println(indexOfDoubleNewLines);
		ArrayList <String> parsedDisplayData =  parseDisplayTextByDate(getIndexOfDoubleEmptyLines(outPutDisplayText), outPutDisplayText);
//		System.out.println(parsedDisplayData.toString());
		
		
		//line between each data chunk denoted by a month/year date
		for (int i = 0; i < parsedDisplayData.size()-1; i++) {
			String chunk = parsedDisplayData.get(i);
//			System.out.print(chunk.substring(0,chunk.length() - 14));
			parsedDisplayData.set(i, chunk.substring(0,chunk.length() - 14));
			
		}
	    Integer finalChunkIndex = parsedDisplayData.size()-1;
	    String finalChunk = parsedDisplayData.get(finalChunkIndex);
	    
		parsedDisplayData.set(finalChunkIndex, finalChunk.substring(0,finalChunk.length()));
		
		for(String chunk : parsedDisplayData) {
			LogData logDataTemp = new LogData();
			logDataTemp.setRawFileContent(chunk);
			output.add(logDataTemp);
		}

		
		return output;
	}
	
	private ArrayList <Integer> getIndexOfDoubleEmptyLines(String displayText){
		ArrayList<Integer> output = new ArrayList <Integer>();
		String doubleEmpLine = "\n\n"; //Looking for chunks denoted by month and year
		String temp = displayText;
		System.out.println(temp.length());
		
		int index = temp.indexOf(doubleEmpLine);
		while (index >= 0) {
			output.add(index + 4); //Takes into account that there is a tab
		    index = temp.indexOf(doubleEmpLine, index + 1);
		}
		return output;
	}
	
	//Function relys on display text displaying the (yyyy-mm-dd\n\n) -> 12 chars,, in a explicit way
	private ArrayList <String> parseDisplayTextByDate(ArrayList <Integer> input, String displayText){
		int offset = 0;
		ArrayList<String> output = new ArrayList <String>();
		//Checking two at a time
		for(int i = 0; i < input.size() -1; i++) {
			output.add(displayText.substring(input.get(i), input.get(i + 1)));
		}
		output.add(displayText.substring(input.get(input.size()-1)));
		return output;
		
	}

}
 