package parking.management.model;

import java.util.Date;

import parking.management.constant.ParkingConstants;

public class Car extends Vehicle {
	public final String vehicleType = ParkingConstants.MEDIUM_VEHICLE_TYPE;

	public Car(String vehicleNumber, Date arrivalTime) {
		super(vehicleNumber, arrivalTime);
	}
	// If in future any specific operations that need to be perform on Car these can
	// be added in this bean.

	public Car() {
		
	}

	@Override
	public String getVehicleType() {
		return vehicleType;
	}

}
