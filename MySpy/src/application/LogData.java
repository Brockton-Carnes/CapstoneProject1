package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Purpose of this class is format date logs and concat time segs
 * We can call LogData when using controller to have correctly formatted input data
 * @author brock
 *
 */
public class LogData {
	
	private ArrayList <TimeSegmant> timeSegmants = new ArrayList<TimeSegmant>();
	private String rawFileContent;
	
	LogData(){};
	
	LogData(String path) {
		try {
			rawFileContent = getFileContentsByStringDate(path);
			parseTimeSegs();
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
	public void setRawFileContent(String rawContent) {
		rawFileContent = rawContent;
		parseTimeSegs();
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
	
	private void parseTimeSegs() {
		Scanner scan = new Scanner(rawFileContent);
		TimeSegmant tempTimeSeg = new TimeSegmant();					//Empty Constructor 
		while (scan.hasNextLine()){
			String timeline = scan.nextLine();
			String contentline = scan.nextLine();
			TimeSegmant timeSeg = new TimeSegmant(timeline, contentline);
			if(tempTimeSeg.getTime().equals(timeSeg.getTime())) {
				tempTimeSeg.concat(timeSeg);
			}
			else {
				timeSegmants.add(tempTimeSeg);
				tempTimeSeg = timeSeg;
			}
			
		}
		timeSegmants.add(tempTimeSeg);
	}
	
	
	
	public String formattedDisplayData() {
		String output = "";
		for (TimeSegmant ts : timeSegmants) output += ts.printSegment();
		return output;
	}
	
	private ArrayList <TimeSegmant> getTimeSegBySearchValue(String sub){
		ArrayList <TimeSegmant> output = new ArrayList<TimeSegmant>();
		for(TimeSegmant ts : timeSegmants) if (ts.getContent().contains(sub)) output.add(ts); //this looks cool but cant read it hahah
		return output;
	}
	
	public String formattedDisplayDataBySearchValue(String sub) {
		String output = "";
		for (TimeSegmant ts : getTimeSegBySearchValue(sub) ) output += ts.printSegment();
		return output;
	}
	
	@Override
	public String toString() {
		return "LogData [timeSegmants=" + timeSegmants + ", rawFileContent=" + rawFileContent + "]";
	}
	
	

}
