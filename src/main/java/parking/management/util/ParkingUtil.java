package parking.management.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import parking.management.constant.ParkingConstants;
import parking.management.exception.InvalidConsoleInput;
import parking.management.exception.InvalidVehicleType;
import parking.management.model.Bus;
import parking.management.model.Car;
import parking.management.model.TwoWheeler;
import parking.management.model.Vehicle;

public class ParkingUtil {

	public static String getVehicleType(Vehicle vehicle) throws InvalidVehicleType {
		if (vehicle instanceof TwoWheeler) {
			TwoWheeler tw = (TwoWheeler) vehicle;
			return tw.vehicleType;
		} else if (vehicle instanceof Car) {
			return ((Car) vehicle).vehicleType;
		} else if (vehicle instanceof Bus) {
			return ((Bus) vehicle).vehicleType;
		}
		throw new InvalidVehicleType("The vehicle type is invalid. Please create valid parking.");
	}

	public static boolean isValidVehicleType(String typeOfVehicle) throws InvalidVehicleType {
		if (null != typeOfVehicle && !(typeOfVehicle.equals(""))) {
			if (typeOfVehicle.equalsIgnoreCase(ParkingConstants.LARGE_VEHICLE_TYPE)
					|| typeOfVehicle.equalsIgnoreCase(ParkingConstants.MEDIUM_VEHICLE_TYPE)
					|| typeOfVehicle.equalsIgnoreCase(ParkingConstants.SMALL_VEHICLE_TYPE)) {
				return true;
			}
		}
		throw new InvalidVehicleType("The vehicle type is invalid it can be " + ParkingConstants.LARGE_VEHICLE_TYPE
				+ " , " + ParkingConstants.MEDIUM_VEHICLE_TYPE + " or " + ParkingConstants.SMALL_VEHICLE_TYPE);
	}

	public static boolean isValidParkingType(String typeOfParking) throws InvalidVehicleType {
		if (null != typeOfParking && !("".equals(typeOfParking))) {
			if (typeOfParking.equalsIgnoreCase(ParkingConstants.REGULAR_PARKING)
					|| typeOfParking.equalsIgnoreCase(ParkingConstants.COMPACT_PARKING)
					|| typeOfParking.equalsIgnoreCase(ParkingConstants.HANDICAPPED_PARKING)) {
				return true;
			}
		}
		throw new InvalidVehicleType("The vehicle type is invalid it can be " + ParkingConstants.REGULAR_PARKING + " , "
				+ ParkingConstants.REGULAR_PARKING + " or " + ParkingConstants.REGULAR_PARKING);
	}

	public static Vehicle getNewVehicle(String vehicleType, String vehicleNo) throws InvalidVehicleType {
		if (vehicleType.equalsIgnoreCase(ParkingConstants.LARGE_VEHICLE_TYPE)) {
			return new Bus(vehicleNo, new Date());
		} else if (vehicleType.equalsIgnoreCase(ParkingConstants.MEDIUM_VEHICLE_TYPE)) {
			return new Car(vehicleNo, new Date());
		} else if (vehicleType.equalsIgnoreCase(ParkingConstants.SMALL_VEHICLE_TYPE)) {
			return new TwoWheeler(vehicleNo, new Date());
		} else {
			throw new InvalidVehicleType("The specified type to create a vehicle is not valid please enter valid type");
		}
	}

	public static String getInputFromConsole() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		//reader.close();
		return input;

	}
	
	public static String getVehicleTypeFromConsole() throws InvalidConsoleInput, IOException {
		int choice = 0;
		String vehicleType = null;
		System.out.println("Enter the type of vehicle :\n 1 - two wheeler \n 2 - car \n 3 - bus");
		String input = ParkingUtil.getInputFromConsole().toLowerCase();
		choice = Integer.parseInt(input);
		switch (choice) {
		case 1:
			vehicleType = ParkingConstants.SMALL_VEHICLE_TYPE;
			break;
		case 2:
			vehicleType = ParkingConstants.MEDIUM_VEHICLE_TYPE;
			break;
		case 3:
			vehicleType = ParkingConstants.LARGE_VEHICLE_TYPE;
			break;
		default:
			throw new InvalidConsoleInput("The vehicle type inserted is not valid can not proceed with the operation");
		}
		return vehicleType;

	}
	
	public static String getParkingTypeFromConsole() throws InvalidConsoleInput, IOException {
		int choice = 0;
		String parkingType = null;
		System.out.println("Enter the type of Parking : \n 1 - regular \n 2 - handicapped \n 3 - compact");
		// Reading data using readLine
		String input = ParkingUtil.getInputFromConsole().toLowerCase();
		choice = Integer.parseInt(input);
		switch (choice) {
		case 1:
			parkingType = ParkingConstants.REGULAR_PARKING;
			break;
		case 2:
			parkingType = ParkingConstants.HANDICAPPED_PARKING;
			break;
		case 3:
			parkingType = ParkingConstants.COMPACT_PARKING;
			break;
		default:
			throw new InvalidConsoleInput("The parking type inserted is not valid can not proceed with the operation");

		}
		return parkingType;

	}
}
