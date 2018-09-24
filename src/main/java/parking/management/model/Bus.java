package parking.management.model;

import java.util.Date;

import parking.management.constant.ParkingConstants;

public class Bus extends Vehicle {
	public final String vehicleType = ParkingConstants.LARGE_VEHICLE_TYPE;

	public Bus(String vehicleNumber, Date arrivalTime) {
		super(vehicleNumber, arrivalTime);
	}

	public Bus() {
		
	}

	@Override
	public String getVehicleType() {
		return vehicleType;
	}

	// If in future any specific operations that need to be perform on Bus theses
	// can be added in this bean.

}
