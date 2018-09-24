package parking.management.service;

import parking.management.exception.InvalidParking;
import parking.management.exception.InvalidParkingType;
import parking.management.exception.InvalidTicket;
import parking.management.exception.InvalidVehicleType;
import parking.management.model.ParkingLot;
import parking.management.model.Ticket;
import parking.management.model.Vehicle;

public interface ParkingService {

	public boolean isParkingEmpty();

	public boolean isParkingFull();

	public boolean isParkingAvailable(String parkingType, String vehicleType)
			throws InvalidVehicleType, InvalidParkingType, InvalidParking;

	public Ticket allocateParking(ParkingLot lot, Vehicle vehcile)
			throws InvalidVehicleType, InvalidParkingType, InvalidParking;

	public Vehicle deallocateParking(int ticketNumber) throws InvalidTicket, InvalidParking;
	
	public void systemCleanUp();

}
