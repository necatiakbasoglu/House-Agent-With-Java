
package businessLayer;

import java.util.ArrayList;

import fileAccessLayer.EstateAgentDAL;
import presentationLayer.EstateAgentGUI;


public class EstateAgent {
	private ArrayList<House> houseList;
	
	EstateAgentDAL estateAgentDAL;
	EstateAgentGUI estateAgentGUI;
	
	public EstateAgent(){
		houseList = new ArrayList<House>();
	
	    // creating EstateAgentDataAccessLayer object
	    String readingPath = "housing.txt";
	    String writingPath = "housing_updated.txt";
	    estateAgentDAL = new EstateAgentDAL(this, readingPath,writingPath);
	    estateAgentDAL.readAllHouses();

	    // creating EstateAgentGraphicalUserInterface object
	    estateAgentGUI = new EstateAgentGUI(this);	

	    estateAgentGUI.createGUI();

	}
	
	public boolean addHouse(House house){
		if(house != null){		
			houseList.add(house);
			return true;
		}
		return false;
	}
	
	public boolean removeHouse(House house){  // remove House with object
		return houseList.remove(house);
	}	

	public boolean removeHouse(int houseId){  // remove House with houseId
		for(House house : houseList){
			if(house.getHouseId()==houseId){
				return removeHouse(house);
			}
		}
		return false;
	}
	
	// save houses to file from table 
	public boolean saveToFileDataFromTable(ArrayList<String> lines){		
		return estateAgentDAL.saveTableDataOnShowed(lines);
	}
	
	public ArrayList<House> getHouseList(){
		return houseList;
	}
	
}