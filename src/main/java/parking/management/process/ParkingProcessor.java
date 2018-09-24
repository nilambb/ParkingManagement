package parking.management.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import parking.management.constant.ParkingConstants;
import parking.management.dao.HistoryDao;
import parking.management.dao.ParkingSpotDao;
import parking.management.dao.TicketDao;
import parking.management.dao.VehicleDao;
import parking.management.exception.InvalidParking;
import parking.management.exception.InvalidParkingType;
import parking.management.exception.InvalidTicket;
import parking.management.exception.InvalidVehicleType;
import parking.management.model.ParkingLot;
import parking.management.model.Ticket;
import parking.management.model.Vehicle;
import parking.management.util.ParkingUtil;

@Component("parkingProcessor")
public class ParkingProcessor {
	@Autowired
	private ParkingSpotDao parkingSpotDao;
	@Autowired
	private VehicleDao vehicleDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private HistoryDao historyDao;

	public boolean isEmpty() {
		return parkingSpotDao.isEmpty();
	}

	public boolean isFull() {
		return parkingSpotDao.isFull();
	}

	public boolean isParkingAvailable(String parkingType, String vehicleType)
			throws InvalidVehicleType, InvalidParkingType, InvalidParking {
		if (ParkingUtil.isValidParkingType(parkingType)) {
			return parkingSpotDao.isParkingAvailable(parkingType, vehicleType);
		} else {
			throw new InvalidParking(
					"The parking data passed was not proper so can not process the request. Please insert valid data");
		}

	}

	public int getSmallParkingSpot(String parkingType) throws InvalidParking {
		if (parkingSpotDao.isParkingSpotAvailable(parkingType, ParkingConstants.SMALL_PARKING_SPOT)) {
			return parkingSpotDao.getParkingSpot(parkingType, ParkingConstants.SMALL_PARKING_SPOT);
		} else if (parkingSpotDao.isParkingSpotAvailable(parkingType, ParkingConstants.MEDIUM_PARKING_SPOT)) {
			return parkingSpotDao.getParkingSpot(parkingType, ParkingConstants.MEDIUM_PARKING_SPOT);
		} else if (parkingSpotDao.isParkingSpotAvailable(parkingType, ParkingConstants.LARGE_PARKING_SPOT)) {
			return parkingSpotDao.getParkingSpot(parkingType, ParkingConstants.LARGE_PARKING_SPOT);
		} else {
			throw new InvalidParking("The parking slot is not available.");
		}
	}

	public int getMediumParkingSpot(String parkingType) throws InvalidParking {
		if (parkingSpotDao.isParkingSpotAvailable(parkingType, ParkingConstants.MEDIUM_PARKING_SPOT)) {
			return parkingSpotDao.getParkingSpot(parkingType, ParkingConstants.MEDIUM_PARKING_SPOT);
		} else if (parkingSpotDao.isParkingSpotAvailable(parkingType, ParkingConstants.LARGE_PARKING_SPOT)) {
			return parkingSpotDao.getParkingSpot(parkingType, ParkingConstants.LARGE_PARKING_SPOT);
		} else {
			throw new InvalidParking("The parking slot is not available. Sorry for the inconvenience.");
		}
	}

	public int getLargeParkingSpot(String parkingType) throws InvalidParking {
		if (parkingSpotDao.isParkingSpotAvailable(parkingType, ParkingConstants.LARGE_PARKING_SPOT)) {
			return parkingSpotDao.getParkingSpot(parkingType, ParkingConstants.LARGE_PARKING_SPOT);
		} else {
			throw new InvalidParking("The parking slot is not available. Sorry for the inconvenience.");
		}
	}

	public Ticket allocateParking(ParkingLot lot, Vehicle vehicle)
			throws InvalidVehicleType, InvalidParkingType, InvalidParking {
		String parkingType = lot.name().toLowerCase();
		if (isParkingAvailable(parkingType, vehicle.getVehicleType())) {
			int parkingSpotId = 0;
			String vehicleType = vehicle.getVehicleType();
			switch (vehicleType.toLowerCase()) {
			case ParkingConstants.SMALL_VEHICLE_TYPE:
				parkingSpotId = getSmallParkingSpot(parkingType);
				break;
			case ParkingConstants.MEDIUM_VEHICLE_TYPE:
				parkingSpotId = getMediumParkingSpot(parkingType);
				break;
			case ParkingConstants.LARGE_VEHICLE_TYPE:
				parkingSpotId = getLargeParkingSpot(parkingType);
				break;
			default:
				throw new InvalidVehicleType(
						"The vehicle type is invalid. Can not allocate parking for " + vehicleType);

			}

			vehicle = vehicleDao.addVehicle(vehicle);
			Ticket ticket = ticketDao.createTicket(vehicle.getId(), parkingSpotId);
			parkingSpotDao.allocateParkingSpot(parkingSpotId);
			return ticket;

		} else {
			throw new InvalidParking("Sorry for the inconviniece but the " + lot + " parking is not available for the "
					+ vehicle + " .");
		}
	}

	public Vehicle deallocateParking(int ticketId) throws InvalidTicket, InvalidParking {
		Vehicle vehicle = null;
		if (ticketDao.isValidTicket(ticketId)) {
			Ticket ticket = ticketDao.getTicket(ticketId);
			vehicle = vehicleDao.getVehicle(ticket.getVehicleId());
			// System.out.println("Vehicle is ");
			ticketDao.deallocateTicket(ticket);
			vehicleDao.detachVehicle(vehicle);
			parkingSpotDao.deallocateParkingSpot(ticket.getParkingSpotId());
			System.out.println("The parking de-allocated successfully...");
			return vehicle;
		} else {
			throw new InvalidTicket("Can not deallocate the parking. The ticket is invalid");
		}

	}

	public void dataBaseCleanup() {
		historyDao.CleanUp();
		ticketDao.cleanUp();
		vehicleDao.cleaUp();

	}
}
