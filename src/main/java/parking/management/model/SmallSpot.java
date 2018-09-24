package parking.management.model;

import parking.management.constant.ParkingConstants;

public class SmallSpot extends ParkingSpot {
	private final String spotType = ParkingConstants.SMALL_PARKING_SPOT;
	private String description;
	public SmallSpot(int id,String  spotNum,ParkingLot lot,boolean occupied) {
		super(id,spotNum,lot,occupied);
	}
	public SmallSpot() {
		
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String getSpotType() {
		return spotType;
	}
	
}
