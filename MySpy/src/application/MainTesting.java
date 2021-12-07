package application;
import application.LogData;

public class MainTesting {
	public static void main(String [] args) {
		String s = "2021-11-21";
		LogData data = new LogData("C:\\Users\\brock\\Desktop\\CapstoneTestData\\" + s + ".txt");
		System.out.println(data.toString());
	}
}


