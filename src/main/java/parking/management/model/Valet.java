package parking.management.model;

import org.springframework.beans.factory.annotation.Autowired;

import parking.management.exception.InvalidParking;
import parking.management.exception.InvalidParkingType;
import parking.management.exception.InvalidTicket;
import parking.management.exception.InvalidVehicleType;
import parking.management.process.ParkingProcessor;
import parking.management.service.ParkingService;
import parking.management.util.ParkingUtil;

public class Valet implements ParkingService {
	// private final String userName;
	// private final String password;

	@Autowired
	private ParkingProcessor parkingProcessor;

	@Override
	public boolean isParkingEmpty() {
		return parkingProcessor.isEmpty();
	}

	@Override
	public boolean isParkingFull() {
		return parkingProcessor.isFull();
	}

	@Override
	public boolean isParkingAvailable(String parkingType, String vehicleType)
			throws InvalidVehicleType, InvalidParkingType, InvalidParking {
		if (ParkingUtil.isValidParkingType(parkingType)) {
			return parkingProcessor.isParkingAvailable(parkingType, vehicleType);
		} else {
			throw new InvalidParking("The parking request is invalid. Please pass valid parameters..");
		}

	}

	@Override
	public Ticket allocateParking(ParkingLot lot, Vehicle vehicle)
			throws InvalidVehicleType, InvalidParkingType, InvalidParking {
		return parkingProcessor.allocateParking(lot, vehicle);
	}

	@Override
	public Vehicle deallocateParking(int ticketNumber) throws InvalidTicket, InvalidParking {
		return parkingProcessor.deallocateParking(ticketNumber);
	}

	@Override
	public void systemCleanUp() {
		parkingProcessor.dataBaseCleanup();
		
	}

}