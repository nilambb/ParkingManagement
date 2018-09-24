package parking.management.model;

import parking.management.constant.ParkingConstants;

public class LargeSpot extends ParkingSpot {

	private final String spotType = ParkingConstants.LARGE_PARKING_SPOT;
	private String description;

	public LargeSpot(int id, String spotNum, ParkingLot lot, boolean occupied) {
		super(id, spotNum, lot, occupied);
	}

	public LargeSpot() {

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
