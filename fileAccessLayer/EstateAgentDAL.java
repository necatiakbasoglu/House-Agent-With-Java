
package fileAccessLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import businessLayer.*;


public class EstateAgentDAL {
	private EstateAgent estateAgent;
	private String readingPath, writingPath;

	public EstateAgentDAL(EstateAgent estateAgent, String readingPath, String writingPath) {
		this.estateAgent = estateAgent;
		this.readingPath = readingPath;
		this.writingPath = writingPath;
	}
	
	private String getTitleByLine(){  // for getting titles from file
		Scanner scanner;		
		try {
			scanner = new Scanner(new File(readingPath));
		} catch (FileNotFoundException e) {
			System.out.println("File doesn not exist!");
			return null;
		}		
		String line = scanner.nextLine();  // only read first line
		scanner.close();
		return line;
	}
	
	
	private String getInfo(StringTokenizer tokenizer){ // check and get information
		if (tokenizer.hasMoreTokens()) {
			return tokenizer.nextToken();
		}
		return null;
	}
	
	private void houseCreator(StringTokenizer tokenizer){
		int count =tokenizer.countTokens();
		int houseId, houseSize, roomNumber, bathrooms;
		double housePrice;
		String airconditioner;
		House house;
		houseId = Integer.valueOf(getInfo(tokenizer));
		housePrice = Double.valueOf(getInfo(tokenizer));
		houseSize = Integer.valueOf(getInfo(tokenizer)); 
		roomNumber = Integer.valueOf(getInfo(tokenizer));
		bathrooms = Integer.valueOf(getInfo(tokenizer));
		
		if(count==7)	// somewhere there is mistake. I don't care about it.
			getInfo(tokenizer);
		
		airconditioner = getInfo(tokenizer);
		house = new House(houseId,houseSize, roomNumber, bathrooms, housePrice, airconditioner);
		//house.setHouseId(houseId);
		estateAgent.addHouse(house);
	}
	
	
	public boolean readAllHouses() { // read houses from file to fill houseList
		Scanner scanner;
		StringTokenizer tokenizer;
		try {
			scanner = new Scanner(new File(readingPath));
		} catch (FileNotFoundException e) {
			System.out.println("File doesn not exist!");
			return false;
		}
		//scanner.nextLine(); // this line has title and skip it
		tokenizer = new StringTokenizer(scanner.nextLine(), ",");		

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			tokenizer = new StringTokenizer(line, ",");						
			houseCreator(tokenizer);
		}
		scanner.close();
		return true;
	}
	
	// save to file for data shown on the table
	public boolean saveTableDataOnShowed(ArrayList<String> lines){
		PrintWriter outputStream;		
		try {
			outputStream = new PrintWriter(new FileOutputStream(writingPath));
		} catch (Exception e) {
			System.out.println("Cannot write to file");
			return false;
		}

		outputStream.println(getTitleByLine());  //write titles

		for (String line : lines) {
			outputStream.println(line);
		}
		
		outputStream.close();
		return true;

	}
	
}
