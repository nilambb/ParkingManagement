package parking.management.model;

import parking.management.constant.ParkingConstants;

public class MediumSpot extends ParkingSpot{
	private final String spotType = ParkingConstants.MEDIUM_PARKING_SPOT;
	private String description;
	
	public MediumSpot(int id,String  spotNum,ParkingLot lot,boolean occupied) {
		super(id,spotNum,lot,occupied);
	}
	
	public MediumSpot( ) {
		
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
