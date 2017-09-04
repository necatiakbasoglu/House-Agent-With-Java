
package businessLayer;

public class House {
	static int autoIncrementId = 0;
	private int houseId;
	private double housePrice;
	private int houseSize;
	private int roomNumber;
	private int bathroomNumber;
	private String airconditioner;
	
	
	public House(int houseSize, int roomNumber, int bathrooms, double housePrice, String airconditioner){
		setHouseId(++autoIncrementId);
		setHousePrice(housePrice);
		setHouseSize(houseSize);
		setRoomNumber(roomNumber);
		setBathrooms(bathrooms);
		setAirconditioner(airconditioner);
	}
	
	///
	public House(int houseId,int houseSize, int roomNumber, int bathrooms,
			double housePrice, String airconditioner){
		setHouseId(houseId);
		setHousePrice(housePrice);
		setHouseSize(houseSize);
		setRoomNumber(roomNumber);
		setBathrooms(bathrooms);
		setAirconditioner(airconditioner);
		autoIncrementId+=1;
	}
	
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public double getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(double housePrice) {
		this.housePrice = housePrice;
	}
	public int getHouseSize() {
		return houseSize;
	}
	public void setHouseSize(int houseSize) {
		this.houseSize = houseSize;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getBathrooms() {
		return bathroomNumber;
	}

	public void setBathrooms(int bathrooms) {
		this.bathroomNumber = bathrooms;
	}

	public String getAirconditioner() {
		return airconditioner;
	}

	public void setAirconditioner(String airconditioner) {
		this.airconditioner = airconditioner;
	}

	public String toFile() {
		return houseId + "," + housePrice + "," + houseSize + "," + roomNumber + "," + bathroomNumber + airconditioner;
	}
	
}
