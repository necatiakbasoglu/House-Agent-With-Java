
package presentationLayer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import businessLayer.EstateAgent;
import businessLayer.House;

public class EstateAgentGUI implements ActionListener{
	JButton removeHousePanelOpenButton, orderHousePanelOpenButton, addHousePanelOpenButton,
	searchHousePanelOpenButton,orderConfrimButton,addHouseConfirmButton
	,removeHouseConfirmButton,searchHouseConfirmButton,
	saveToFileButton,cancelSortingOperationsButton,returnButton;
	JRadioButton ascendingPriceRadioButton ,descendingPriceRadioButton;
	JTextField textFieldPriceOfHouse,textFieldSizeOfHouse,textFieldRoomsOfHouse,
	textFieldBadRoomsOfHouse,textFieldRemoveHouseByID,textFieldMinBounds,textFieldMaxBounds,
	textFieldNumberOfRoomsForSearch;
	JLabel labelPriceOfHouse,labelSizeOfHouse,labelRoomsOfHouse,labelBadRoomsOfHouse,
	labelAirConditionOfHouse,labelRemoveHouseByID,labelLowerBoundsForSearch,
	labelUpperBoundsForSearch,labelNumberOfRoomsForSearch,labelErrorForAddHouse,labelErrorForRemoveHouse;
	JComboBox<String> comboBoxAirConditionOfHouse, comboBoxSearcingChoice;
	ButtonGroup radioButtonGroup;
	JTable housesTable;
	DefaultTableModel tableModel ;
	TableRowSorter<TableModel> tableRowSorter;
	JFrame estateAgentFrame;
	Container cPane, orderHousesContainer,addHousesContainer, removeHousesContainer,searchHousesContainer;
	CardLayout cardLayout;
	GroupLayout groupLayout; 
	JScrollPane leftSideOfGUI;
	JPanel rightSideOfGUI;
	EstateAgent estateAgent;
	
	public EstateAgentGUI(EstateAgent estateAgent){
		this.estateAgent  = estateAgent;
	}
	
	public void createGUI(){
		estateAgentFrame = new JFrame("Estate Agent");
		createcPane();
		estateAgentFrame.add(cPane);
		estateAgentFrame.setVisible(true);
		estateAgentFrame.setSize(700, 500);
		estateAgentFrame.setResizable(false);
		estateAgentFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
			int choice = JOptionPane.showConfirmDialog(estateAgentFrame, "Do you want to save ?"
					,"Close",
					JOptionPane.YES_OPTION,JOptionPane.WARNING_MESSAGE);
			if(choice == JOptionPane.YES_OPTION){
				if(estateAgent.saveToFileDataFromTable(createDataToSave()))
					JOptionPane.showMessageDialog(estateAgentFrame, "Informations are saved to file \n"
							+ "as 'housing_updated.txt'");
			}else if(choice == JOptionPane.NO_OPTION)
				System.exit(-1);
			estateAgentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}	
		});
	}
	
	private void createcPane(){
		//create main pane
		cPane = new Container();
		createLeftSideOfGUI();
		createRightSideOfGUI();
		groupLayout  = new GroupLayout(cPane);
		cPane.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addComponent(leftSideOfGUI)
				.addGroup(groupLayout.createParallelGroup()
						.addComponent(rightSideOfGUI)
						));
		
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
				.addComponent(leftSideOfGUI)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(rightSideOfGUI)
						));
		cPane.setVisible(true);
	}
	
	public void createLeftSideOfGUI(){
		//create left side of GUI and Jtable
		createDefaultTableModel();
		fillTable();
		housesTable = new JTable(tableModel);
		housesTable.setEnabled(false);
		leftSideOfGUI = new JScrollPane(housesTable);
		
	}
	
	public void createRightSideOfGUI(){
		rightSideOfGUI = new JPanel();
		cardLayout = new CardLayout();		
		rightSideOfGUI.setLayout(cardLayout);		//set right side layout as card layout
		rightSideOfGUI.add(createRightSideOfGUIContainer());//create right side component holder
		createOrderHousesContainer();				//create ordering house menu's holder
		createAddHousesContainer();					//create adding house menu's holder
		createRemoveHousesContainer();				//create removing house menu's holder
		createSearcHousesContainer();				//create searching house menu's holder
		comboBoxSearcingChoice.addActionListener(this);//create action listener
		rightSideOfGUI.add(orderHousesContainer,"Order House");//add holders to right side of GUI's holder
		rightSideOfGUI.add(addHousesContainer,"Add House");
		rightSideOfGUI.add(removeHousesContainer,"Remove House");
		rightSideOfGUI.add(searchHousesContainer,"Search House");
	}
	
	private Container createRightSideOfGUIContainer(){
		//create right side of GUI main container and fill with necessary buttons
		Container rightSideOfGUIContainer = new Container();
		rightSideOfGUIContainer.setLayout(new BoxLayout(rightSideOfGUIContainer, BoxLayout.PAGE_AXIS));
		createButtons();	//create components
		createTextFields(); 
		createLabels();	
		rightSideOfGUIContainer.add(orderHousePanelOpenButton);
		rightSideOfGUIContainer.add(Box.createVerticalStrut(5)); //create blank between components
		rightSideOfGUIContainer.add(searchHousePanelOpenButton);
		rightSideOfGUIContainer.add(Box.createVerticalStrut(5));
		rightSideOfGUIContainer.add(addHousePanelOpenButton);
		rightSideOfGUIContainer.add(Box.createVerticalStrut(5));
		rightSideOfGUIContainer.add(removeHousePanelOpenButton);
		rightSideOfGUIContainer.add(Box.createVerticalStrut(5));
		rightSideOfGUIContainer.add(cancelSortingOperationsButton);
		rightSideOfGUIContainer.add(Box.createVerticalStrut(5));
		rightSideOfGUIContainer.add(saveToFileButton);
		return rightSideOfGUIContainer;
	}
	
	private void createButtons(){							//create all necessary buttons
		orderHousePanelOpenButton = new JButton("Order House");
		searchHousePanelOpenButton = new JButton("Search House");
		addHousePanelOpenButton = new JButton("Add House");
		removeHousePanelOpenButton = new JButton("Remove House");
		orderConfrimButton = new JButton("OK");
		addHouseConfirmButton = new JButton("Save");
		removeHouseConfirmButton = new JButton("REMOVE");
		searchHouseConfirmButton = new JButton("SEARCH");
		saveToFileButton = new JButton("SAVE");
		returnButton = new JButton("Return");
		cancelSortingOperationsButton = new JButton("Cancel Sort");
		cancelSortingOperationsButton.addActionListener(this);
		saveToFileButton.addActionListener(this);
		removeHouseConfirmButton.addActionListener(this);
		orderHousePanelOpenButton.addActionListener(this);
		orderConfrimButton.addActionListener(this);
		addHousePanelOpenButton.addActionListener(this);
		addHouseConfirmButton.addActionListener(this);
		removeHousePanelOpenButton.addActionListener(this);
		searchHousePanelOpenButton.addActionListener(this);
		searchHouseConfirmButton.addActionListener(this);
		returnButton.addActionListener(this);
		setButtonSizeOnRightSizeOfGUI();                //set all button size equal
	}
	
	private void setButtonSizeOnRightSizeOfGUI(){//set all button size equal according to greatest button
		Dimension max = removeHousePanelOpenButton.getMaximumSize();
		Dimension min = removeHousePanelOpenButton.getMinimumSize();
		removeHouseConfirmButton.setMaximumSize(max);
		returnButton.setMaximumSize(max);
		searchHouseConfirmButton.setMaximumSize(max);
		orderConfrimButton.setMaximumSize(max);
		addHouseConfirmButton.setMaximumSize(max);
		addHousePanelOpenButton.setMaximumSize(max);
		removeHousePanelOpenButton.setMaximumSize(max);
		orderHousePanelOpenButton.setMaximumSize(max);
		searchHousePanelOpenButton.setMaximumSize(max);
		saveToFileButton.setMaximumSize(max);
		cancelSortingOperationsButton.setMaximumSize(max);
		returnButton.setMinimumSize(min);
		searchHouseConfirmButton.setMinimumSize(min);
		orderConfrimButton.setMinimumSize(min);
		addHouseConfirmButton.setMinimumSize(min);
		removeHouseConfirmButton.setMinimumSize(min);
		addHousePanelOpenButton.setMinimumSize(min);
		removeHousePanelOpenButton.setMinimumSize(min);
		orderHousePanelOpenButton.setMinimumSize(min);
		searchHousePanelOpenButton.setMinimumSize(min);
		saveToFileButton.setMinimumSize(min);
		cancelSortingOperationsButton.setMinimumSize(min);
	}
	
	private void createTextFields(){ 						//create all textFields  
		ArrayList<Component> textFields = new ArrayList<Component>();
		textFieldBadRoomsOfHouse = new JTextField();
		textFieldPriceOfHouse = new JTextField();
		textFieldRoomsOfHouse = new JTextField();
		textFieldSizeOfHouse = new JTextField();
		textFieldRemoveHouseByID = new JTextField();
		textFieldNumberOfRoomsForSearch = new JTextField();
		textFieldMinBounds = new JTextField();
		textFieldMaxBounds = new JTextField();
		textFields.add(textFieldBadRoomsOfHouse);
		textFields.add(textFieldPriceOfHouse);
		textFields.add(textFieldRemoveHouseByID);
		textFields.add(textFieldRoomsOfHouse);
		textFields.add(textFieldSizeOfHouse);
		textFields.add(textFieldNumberOfRoomsForSearch);
		textFields.add(textFieldMaxBounds);
		textFields.add(textFieldMinBounds);
		setComponentDimension(textFields);
	}
	
	private void setComponentDimension(ArrayList<Component> list){ //set all component dimension are equal
		Dimension dimension = new Dimension(350, 30);
		for(Component c : list){
			c.setMaximumSize(dimension);
		}
	}
	
	private void createLabels(){								//create all labels
		labelAirConditionOfHouse = new JLabel("Air Condition :");
		labelBadRoomsOfHouse  =new JLabel("Bad Rooms :");
		labelPriceOfHouse =  new JLabel("Price :");
		labelRoomsOfHouse =  new JLabel("Rooms :");
		labelSizeOfHouse = new JLabel("Size :");
		labelRemoveHouseByID = new JLabel("House ID :");
		labelNumberOfRoomsForSearch = new JLabel("Number of rooms :");
		labelLowerBoundsForSearch =  new JLabel("Lower Bounds :");
		labelUpperBoundsForSearch = new JLabel("Upper Bounds :");
		labelErrorForAddHouse = new JLabel("All Field Must be Full!!!!!");
		labelErrorForRemoveHouse = new JLabel("Invalid Index Plese Check It!");
		labelErrorForAddHouse.setForeground(Color.RED);
		labelErrorForRemoveHouse.setForeground(Color.red);
	}
	
	private void sortCancelOperation(){	
		if(housesTable.getRowSorter()!=null){
			housesTable.getRowSorter().modelStructureChanged();
		}
	}
	public void actionPerformed(ActionEvent e){ 	//all set action performed 
		if(e.getSource()==orderHousePanelOpenButton){
			orderHousesContainer.add(returnButton); //we use adding operation for that button here so that 
			cardLayout.show(rightSideOfGUI, "Order House");//one component can be used multiple panels
			
		}
		
		if(e.getSource()==returnButton){
			clearInsideOfAllTextFields();
			cardLayout.first(rightSideOfGUI);
		}
		
		if(e.getSource()==orderConfrimButton){
			sortTableByAscendingOrDescendingPrice(1);
			cardLayout.first(rightSideOfGUI);
		}
		if(e.getSource()==addHousePanelOpenButton){
			addHousesContainer.add(returnButton);//we use adding operation here for that button so that 
			cardLayout.show(rightSideOfGUI, "Add House");//one component can be used multiple panels
		}
		if(e.getSource()==addHouseConfirmButton){
			sortCancelOperation();//cancel sort operation before add for adding successfully
			if(addHouse()){
				cardLayout.first(rightSideOfGUI); //jump main card on the layout
			}
			sortTableByAscendingOrDescendingPrice(1);//apply sort operation after adding
			clearInsideOfAllTextFields();
		}
		if(e.getSource()==removeHousePanelOpenButton){
			removeHousesContainer.add(returnButton);//we use adding operation for that button here so that 
			cardLayout.show(rightSideOfGUI, "Remove House");//one component can be used multiple panels
		}
		if(e.getSource()==removeHouseConfirmButton){
			sortCancelOperation();//cancel sort operation before remove for removing successfully
			if(removeHouse()){
				cardLayout.first(rightSideOfGUI);
			}
			sortTableByAscendingOrDescendingPrice(1); //apply last sort operation after removing
			clearInsideOfAllTextFields();			 //clear all textFields after operations
		}
		if(e.getSource()==searchHouseConfirmButton){
			search();								 //search operation
			sortTableByAscendingOrDescendingPrice(1);//apply filter operation after searching
			clearInsideOfAllTextFields();
			cardLayout.first(rightSideOfGUI);
		}
		if(e.getSource()==searchHousePanelOpenButton){
			searchHousesContainer.add(returnButton);//we use adding operation for that button here so that one component can be used multiple panels 
			cardLayout.show(rightSideOfGUI, "Search House"); //jump related panel on layout
		}
		if(e.getSource() == comboBoxSearcingChoice){
			changeVisibiltyBySearchingChoice();            //for searching change textBoxs visibility
		}
		if(e.getSource() == cancelSortingOperationsButton){
			sortCancelOperation();
			tableModel.fireTableDataChanged();
			tableRowSorter.setRowFilter(null);
			lockAutoSortOnJTable(); 				//avoid auto sort on the Jtable Column when click it 
		}
		if(e.getSource() == saveToFileButton){
			if(estateAgent.saveToFileDataFromTable(createDataToSave()))
				JOptionPane.showMessageDialog(estateAgentFrame, "Informations are saved to file \n"
						+ "as 'housing_updated.txt'");
		}
			
	}
	
	private void changeVisibiltyBySearchingChoice(){	//for search panel according to selection change 
		String selectedItem = (String)comboBoxSearcingChoice.getSelectedItem();//visibilities of components 
		if(selectedItem.equals("By Size") || 
				selectedItem.equals("By Price")){      //if choice is price or size
			labelLowerBoundsForSearch.setVisible(true);//set visible necessary components
			labelUpperBoundsForSearch.setVisible(true);
			textFieldMinBounds.setVisible(true);
			textFieldMaxBounds.setVisible(true);
			labelNumberOfRoomsForSearch.setVisible(false);
			textFieldNumberOfRoomsForSearch.setVisible(false);
		}else if(selectedItem == "By Number Of Rooms"){
			labelNumberOfRoomsForSearch.setVisible(true);
			textFieldNumberOfRoomsForSearch.setVisible(true);
			labelLowerBoundsForSearch.setVisible(false);
			labelUpperBoundsForSearch.setVisible(false);
			textFieldMinBounds.setVisible(false);
			textFieldMaxBounds.setVisible(false);
		}		
	}
	
	private void createSearcHousesContainer(){ 		//create searching house menu's container + 
		searchHousesContainer = new Container();	// + for card layout
		comboBoxSearcingChoice = new JComboBox<String>();
		comboBoxSearcingChoice.setMaximumSize(new Dimension(350, 30));
		comboBoxSearcingChoice.addItem("By Price");  //set comboBox items
		comboBoxSearcingChoice.addItem("By Number Of Rooms");
		comboBoxSearcingChoice.addItem("By Size");
		searchHousesContainer.setLayout(new BoxLayout(searchHousesContainer, BoxLayout.PAGE_AXIS));
		searchHousesContainer.add(comboBoxSearcingChoice);//add necessary components to container
		searchHousesContainer.add(labelLowerBoundsForSearch);
		searchHousesContainer.add(textFieldMinBounds);
		searchHousesContainer.add(labelUpperBoundsForSearch);
		searchHousesContainer.add(textFieldMaxBounds);
		searchHousesContainer.add(labelNumberOfRoomsForSearch);
		searchHousesContainer.add(textFieldNumberOfRoomsForSearch);
		searchHousesContainer.add(searchHouseConfirmButton);
		labelNumberOfRoomsForSearch.setVisible(false); //set initial visibility
		textFieldNumberOfRoomsForSearch.setVisible(false);
	}
	private void createOrderHousesContainer(){	//create ordering house menu's container + 
		orderHousesContainer = new Container(); //and fill it with necessary components
		orderHousesContainer.setLayout(new BoxLayout(orderHousesContainer, BoxLayout.PAGE_AXIS));
		ascendingPriceRadioButton = new JRadioButton("Ascending Price");
		descendingPriceRadioButton = new JRadioButton("Descending Price");
		radioButtonGroup = new ButtonGroup(); //create radio button group for radio button selection 
		radioButtonGroup.add(ascendingPriceRadioButton);//can be singular at each time
		radioButtonGroup.add(descendingPriceRadioButton);
		radioButtonGroup.clearSelection();
		orderHousesContainer.add(ascendingPriceRadioButton);
		orderHousesContainer.add(descendingPriceRadioButton);
		orderHousesContainer.add(orderConfrimButton);
	}
	
	private void createRemoveHousesContainer(){		//create ordering house menu's container 
		removeHousesContainer = new Container();	//and fill it with necessary components
		removeHousesContainer.setLayout(new BoxLayout(removeHousesContainer, BoxLayout.PAGE_AXIS));
		removeHousesContainer.add(labelRemoveHouseByID);
		removeHousesContainer.add(textFieldRemoveHouseByID);
		removeHousesContainer.add(labelErrorForRemoveHouse);
		removeHousesContainer.add(removeHouseConfirmButton);
		labelErrorForRemoveHouse.setVisible(false); //set initially error label not visible
	}
	
	private void createAddHousesContainer(){												//create ordering house menu's container  
		addHousesContainer = new Container();												//and fill it with necessary components 
		addHousesContainer.setLayout(new BoxLayout(addHousesContainer, BoxLayout.PAGE_AXIS));//create labels and button for this container
		comboBoxAirConditionOfHouse = new JComboBox<String>();								//and add it to this
		comboBoxAirConditionOfHouse.addItem("yes");		
		comboBoxAirConditionOfHouse.addItem("no");
		comboBoxAirConditionOfHouse.setMaximumSize(new Dimension(350, 30));
		addHousesContainer.add(labelPriceOfHouse);
		addHousesContainer.add(textFieldPriceOfHouse);
		addHousesContainer.add(labelSizeOfHouse);
		addHousesContainer.add(textFieldSizeOfHouse);
		addHousesContainer.add(labelRoomsOfHouse);
		addHousesContainer.add(textFieldRoomsOfHouse);
		addHousesContainer.add(labelBadRoomsOfHouse);
		addHousesContainer.add(textFieldBadRoomsOfHouse);
		addHousesContainer.add(labelAirConditionOfHouse);
		addHousesContainer.add(comboBoxAirConditionOfHouse);
		addHousesContainer.add(labelErrorForAddHouse);
		addHousesContainer.add(addHouseConfirmButton);
		labelAirConditionOfHouse.setVisible(true);
		labelBadRoomsOfHouse.setVisible(true);
		labelPriceOfHouse.setVisible(true);
		labelRoomsOfHouse.setVisible(true);
		labelSizeOfHouse.setVisible(true);
		labelErrorForAddHouse.setVisible(false);
	}
	
	private void createDefaultTableModel(){			//create table model for showing and adding data
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Id");				//create columns of model	
		tableModel.addColumn("Price");
		tableModel.addColumn("Size");
		tableModel.addColumn("Rooms");
		tableModel.addColumn("BathRooms");
		tableModel.addColumn("Airconditioner");
		tableRowSorter  = new TableRowSorter<TableModel>(tableModel);//create row sorter and set table model
	}
	
	public void fillTable(){							//fill table with data which is come from 
		for(House house : estateAgent.getHouseList()){	//business layer
			tableModel.addRow(new Object[]{house.getHouseId(),house.getHousePrice(),
					house.getHouseSize(),house.getRoomNumber(),house.getBathrooms(),
					house.getAirconditioner()});
		}
	}
	
	private void clearInsideOfAllTextFields(){		//clear all textFields
		textFieldBadRoomsOfHouse.setText("");
		textFieldMaxBounds.setText("");
		textFieldMinBounds.setText("");
		textFieldNumberOfRoomsForSearch.setText("");
		textFieldPriceOfHouse.setText("");
		textFieldRemoveHouseByID.setText("");
		textFieldRoomsOfHouse.setText("");
		textFieldSizeOfHouse.setText("");
	}
	
	private boolean textFieldCheckerForAddOperation(){	//check textFields for inputs null or not
		if(textFieldPriceOfHouse.getText().matches("\\d+")){			
			if(textFieldSizeOfHouse.getText().matches("\\d+"))
				if(textFieldRoomsOfHouse.getText().matches("\\d+"))
					if(textFieldBadRoomsOfHouse.getText().matches("\\d+"))
						return true;
		}
		return false;
	}
	
	
	public void search(){								//search operation
		String item = (String) comboBoxSearcingChoice.getSelectedItem();
		double lower=0.0;							//initial values for search 	
		double higher=0.0;							//if all textFields is null
		int numberOfRooms=0;						//search operation runs with these values
		if(!textFieldMinBounds.getText().equals(""))
			lower=Double.parseDouble(textFieldMinBounds.getText());
		if(!textFieldMaxBounds.getText().equals(""))
			higher=Double.parseDouble(textFieldMaxBounds.getText());
		if(!textFieldNumberOfRoomsForSearch.getText().equals(""))
			numberOfRooms = Integer.parseInt(textFieldNumberOfRoomsForSearch.getText());
		switch(item){
		case "By Price":
			searchByPrice(lower, higher); //run searchByPrice method with values which is passed
			break;
		case "By Size":
			searchBySize((int)lower, (int)higher);//run searchBySize method with values which is passed
			break;								  //cast from double to int because in the method arguments type is double			
		case "By Number Of Rooms":					
			searchByRoom(numberOfRooms);	//run searchByRoom method with value which is passed
		}
	}
	
	public void searchByRoom(int roomNumber){	//create row filter for related column and related value
		RowFilter<Object, Integer> priceFilter = new RowFilter<Object, Integer>(){
			 public boolean include(Entry<? extends Object, ? extends Integer> entry) {
				 for (int i = entry.getValueCount() - 1; i >= 0; i--) {
				       if (Double.parseDouble(entry.getStringValue(3))==roomNumber) {// 3 refers to column number
				         return true; 
				       }
				     }
			     return false;
			   }
		};
		tableRowSorter.setRowFilter(priceFilter); //set filter for rowSorter
	}
	
	
	public void searchBySize(int lower,int higher){//create row filter for related column and related values
		RowFilter<Object, Integer> priceFilter = new RowFilter<Object, Integer>(){
			 public boolean include(Entry<? extends Object, ? extends Integer> entry) {
				 for (int i = entry.getValueCount() - 1; i >= 0; i--) {
				       if (Double.parseDouble(entry.getStringValue(2))>=lower &&	
				    		   Double.parseDouble(entry.getStringValue(2))<=higher) {//2 refers to column number
				         return true;
				       }
				     }
			     return false;
			   }
		};
		tableRowSorter.setRowFilter(priceFilter); //set filter for RowSorter
	}
	
	public void searchByPrice(double lower,double higher){ //create row filter for related column and related values
		RowFilter<Object, Integer> priceFilter = new RowFilter<Object, Integer>(){
			 public boolean include(Entry<? extends Object, ? extends Integer> entry) {
				 for (int i = entry.getValueCount() - 1; i >= 0; i--) {
				       if (Double.parseDouble(entry.getStringValue(1))>=lower &&
				    		   Double.parseDouble(entry.getStringValue(1))<=higher) {//1 refers to column number
				         return true;
				       }
				     }
			     return false;
			   }
		};
		tableRowSorter.setRowFilter(priceFilter); //set filter for RowSorter
	}
	
	public void sortTableByAscendingOrDescendingPrice(int columnIndex){//sort operation with given column numbers
		housesTable.setRowSorter(tableRowSorter);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
		if(ascendingPriceRadioButton.isSelected())
			sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING));
		else if(descendingPriceRadioButton.isSelected())
			sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.DESCENDING));
		tableRowSorter.setSortKeys(sortKeys);
		tableRowSorter.sort();
		lockAutoSortOnJTable();
	}
	
	private void lockAutoSortOnJTable(){    //block sort operation on the column when is clicked 
		tableRowSorter.setSortable(0, false); //first value refers to column number second isSortable
		tableRowSorter.setSortable(1, false);
		tableRowSorter.setSortable(2, false);
		tableRowSorter.setSortable(3, false);
		tableRowSorter.setSortable(4, false);
		tableRowSorter.setSortable(5, false);
	}
	public boolean addHouse(){				//add house operation when add button is pressed		
		int houseSize,roomNumber,bathrooms; 
		double housePrice;
		String airconditioner;
		boolean flag = false;
		if(textFieldCheckerForAddOperation()){
			housePrice = Double.valueOf(textFieldPriceOfHouse.getText());
			houseSize = Integer.parseInt(textFieldSizeOfHouse.getText());
			roomNumber = Integer.parseInt(textFieldRoomsOfHouse.getText());
			bathrooms = Integer.parseInt(textFieldBadRoomsOfHouse.getText());
			airconditioner = (String) comboBoxAirConditionOfHouse.getSelectedItem();
			House newHouse = new House(houseSize, roomNumber, bathrooms, housePrice, airconditioner);
			estateAgent.addHouse(newHouse);					//add first house list and add Jtable 
			tableModel.addRow(new Object[]{newHouse.getHouseId(),housePrice,
				houseSize,roomNumber,bathrooms,airconditioner});
			labelErrorForAddHouse.setVisible(false);
			flag=true;
			showMessage(addHousesContainer, "Informations added successfully!");//after add operation show message
		}else
			labelErrorForAddHouse.setVisible(true);     //if textFields null give an error
		return flag;
	}
	
	private void showMessage(Component container,String message){
		JOptionPane.showMessageDialog(container, message);
	}
	
	public boolean removeHouse(){ //remove house operation when remove button is pressed
		int houseId;
		boolean flag=false;
		if(textFieldRemoveHouseByID.getText().matches("\\d+")){
			houseId = Integer.valueOf(textFieldRemoveHouseByID.getText());
			if(estateAgent.removeHouse(houseId)){
				removeHouseFromTable(houseId);
				labelErrorForRemoveHouse.setVisible(false);
				flag = true;
				showMessage(addHousesContainer, "Informations removed successfully!");
			}else
				{
					labelErrorForRemoveHouse.setText("ID Not Found!!");
					labelErrorForRemoveHouse.setVisible(true);
				}
		}else
			labelErrorForRemoveHouse.setVisible(true);
		return flag;	
	}
	
	public ArrayList<String> createDataToSave(){   //create array list to save file
		ArrayList<String> lines = new ArrayList<String>();
		for(int i=0; i<housesTable.getRowCount();i++){  //bring data from table 
			lines.add(housesTable.getValueAt(i, 0)+", "+
					housesTable.getValueAt(i, 1)+", "+
					housesTable.getValueAt(i, 2)+", "+
					housesTable.getValueAt(i, 3)+", "+
					housesTable.getValueAt(i, 4)+", "+
					housesTable.getValueAt(i, 5));
		}
		return lines;
	}
	public void removeHouseFromTable(int id){		//for remove house from table 
		for(int i=0; i<tableModel.getRowCount();i++){
			if(String.valueOf(id).equals((tableModel.getValueAt(i, 0)).toString()))
				tableModel.removeRow(i);
			}
	}
}

