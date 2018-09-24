package parking.management.model;

import java.util.Date;

import parking.management.constant.ParkingConstants;

public class TwoWheeler extends Vehicle {
	public final String vehicleType = ParkingConstants.SMALL_VEHICLE_TYPE;
	
	public TwoWheeler(String vehicleNumber, Date arrivalTime) {
		super(vehicleNumber,arrivalTime);
	}

	public TwoWheeler() {

	}

	@Override
	public String getVehicleType() {
		return vehicleType;
	}

  // If in future any specific operations that need to be perform on TwpWheeler these can be added in this bean.
	
}
