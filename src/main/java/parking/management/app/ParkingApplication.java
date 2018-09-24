package parking.management.app;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import parking.management.configuration.ParkingConfiguration;
import parking.management.constant.ParkingConstants;
import parking.management.exception.InvalidConsoleInput;
import parking.management.exception.InvalidParking;
import parking.management.exception.InvalidParkingType;
import parking.management.exception.InvalidTicket;
import parking.management.exception.InvalidVehicleType;
import parking.management.model.ParkingLot;
import parking.management.model.Ticket;
import parking.management.model.Vehicle;
import parking.management.service.ParkingService;
import parking.management.util.ParkingUtil;

public class ParkingApplication implements Runnable {

	private static final Logger logger = Logger.getLogger(ParkingApplication.class);

	public boolean isStop = false;

	public static void main(String[] args) {
		System.out.println("Starting the Parking Management system...");
		Thread parkingApp = new Thread(new ParkingApplication());
		parkingApp.start();
		System.out.println("Parking Management system started");
		logger.info("Parking service is star1ted.....");
	}

	@Override
	public void run() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ParkingConfiguration.class);
		ParkingService service = (ParkingService) context.getBean("valet");

		while (!(this.isStop)) {
			manageParking(service, context);
		}
		System.out.println("You have stopped the valate parking service..... Good Bye!!!!...........");
		logger.info("Stopped the parking service....");
		if (context != null) {
			((AnnotationConfigApplicationContext) context).close();
		}

	}

	public void manageParking(ParkingService service, ApplicationContext context) {
		int valetChoice = 0;
		System.out.println("===========================================================");
		System.out.println("\nPress 1 : Parking is totally empty");
		System.out.println("\nPress 2 : Parking is full");
		System.out.println("\nPress 3 : Parking is available");
		System.out.println("\nPress 4 : Allocate parking");
		System.out.println("\nPress 5 : De-allocate parking");
		System.out.println("\nPress 6 : Stop the application........");
		System.out.println("===========================================================");

		// Reading data using readLine
		try {
			System.out.println("\n\n--------------Enter you choice-----------------");
			String input = ParkingUtil.getInputFromConsole();
			valetChoice = Integer.parseInt(input);
		} catch (Exception e) {
			System.out.println("An error occurred while getting the input. Please enter a valid number");
		}

		try {
			porocessParkingRequests(valetChoice, service, context);
		} catch (InvalidConsoleInput | IOException e) {
			System.out.println("You have entered wrong data. Please make valid selections...");
			logger.error("An exception occrred.. User entered wrong data. Please make valid selections...", e);
		} catch (InvalidVehicleType e) {
			System.out.println(
					"The vehicle type is not valid can not process the request. Please enter the valid vehicle type");
			logger.error("The enetered vehicle type in not suported. ", e);
		} catch (InvalidParkingType e) {
			System.out.println("Invalid parking type. Please make valid selections. Can not process the request");
			logger.error("An invalid parking is selected... ", e);
		} catch (InvalidParking e) {
			System.out.println("Invalid parking request. Can not process the parking request");
			logger.error("An error occurred while processing the parking request..", e);
		} catch (InvalidTicket e) {
			System.out.println("Ticket is not present in system. Invalid ticket.");
			logger.error("Invalid ticket data, can not process the request..", e);
		} catch (Exception e) {
			System.out
					.println("An problem occurred while processing the request please try again and enter valid data.");
			logger.error("An exception occurred while processing the request ", e);

		}
	}

	public void porocessParkingRequests(int valetChoice, ParkingService service, ApplicationContext context)
			throws InvalidConsoleInput, IOException, InvalidVehicleType, InvalidParkingType, InvalidParking,
			InvalidTicket {
		switch (valetChoice) {
		case 1:
			if (service.isParkingEmpty()) {
				System.out.println("***********************Parking is empty***********************");
			} else {
				System.out.println(
						"*********************Parking is not empty some vehicle are still parked in **********************");
			}
			break;
		case 2:
			if (service.isParkingFull()) {
				System.out.println("*****************Parking is full. No space available for parking***************");
			} else {
				System.out.println("******************Parking is not full*******************************");
			}
			break;
		case 3:
			checkAvailablity(service, context);
			break;
		case 4:
			allocateParking(service, context);
			break;
		case 5:
			deallocateParking(service, context);
			break;
		case 6:
			System.out.println(
					"Stop request is generated parform clean up activity and stopping parking management service..... ");
			service.systemCleanUp();
			isStop = true;
			logger.info("User requested to stop the serice.... Good Bye!!!...............");
			break;
		default:
			System.out.println("Entered invalid choice. Please rentered the correct choice............");
			logger.debug("User entered the wrong choice for parking request...." + valetChoice);

		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("A problem occurred while processing the service");
			logger.error("An exception occerred while pausing the thread " + e);
		}
	}

	private void checkAvailablity(ParkingService service, ApplicationContext context)
			throws InvalidConsoleInput, IOException, InvalidVehicleType, InvalidParkingType, InvalidParking {
		if (service.isParkingAvailable(ParkingUtil.getParkingTypeFromConsole().toLowerCase(),
				ParkingUtil.getVehicleTypeFromConsole())) {
			System.out.println("**********************The parking the available*************");
		} else {
			System.out.println("************Sorry the specified parking is not available at this time. Try some time later************");
		}
	}

	private void allocateParking(ParkingService service, ApplicationContext context)
			throws InvalidConsoleInput, IOException, InvalidVehicleType, InvalidParkingType, InvalidParking {
		ParkingLot lot;
		String parkingType = ParkingUtil.getParkingTypeFromConsole();
		switch (parkingType) {
		case "compact":
			lot = ParkingLot.COMPACT;
			break;
		case "handicapped":
			lot = ParkingLot.HANDICAPPED;
			break;
		default:
			lot = ParkingLot.REGULAR;
		}
		String vehicleType = ParkingUtil.getVehicleTypeFromConsole();
		String vehicleNumber;
		System.out.println("Enter the vehicle number");
		vehicleNumber = ParkingUtil.getInputFromConsole();
		Vehicle vehicle = createVehicle(vehicleNumber, vehicleType, context);

		Ticket ticket = service.allocateParking(lot, vehicle);
		System.out.println("************************************************************************************");
		System.out.println("Ticket's details are : \n");
		System.out.println(ticket.toString());
		System.out.println("************************************************************************************");
	}
/**
 * 
 * @param service
 * @param context
 * @throws IOException
 * @throws InvalidTicket
 * @throws InvalidParking
 */
	private void deallocateParking(ParkingService service, ApplicationContext context)
			throws IOException, InvalidTicket, InvalidParking {
		String input;
		Vehicle vehicle = null;
		System.out.println("Enter the ticket numeber : ");
		input = ParkingUtil.getInputFromConsole();
		int ticketId = Integer.parseInt(input);

		/*System.out.println("Enter the parking spot number : ");
		input = ParkingUtil.getInputFromConsole();
		int parkingSpotid = Integer.parseInt(input);

		System.out.println("Enter the vehicle id : ");
		input = ParkingUtil.getInputFromConsole();
		int vehicleId = Integer.parseInt(input);
		Ticket ticket = (Ticket) context.getBean("ticket");
		ticket.setId(ticketId);
		ticket.setVehicleId(vehicleId);
		ticket.setParkingSpotId(parkingSpotid);
		ticket.setDepartureTime(new Date());
*/
		vehicle = service.deallocateParking(ticketId);
		System.out.println("\n=========================================================================");
		System.out.println("Here is the vehicle de-allocating parking is \n" + vehicle.toString());
		System.out.println("==========================================================================");
	}

	/**
	 * 
	 * @param vehicleNumber
	 * @param vehicleType
	 * @param context
	 * @return
	 * @throws InvalidVehicleType
	 */
	public static Vehicle createVehicle(String vehicleNumber, String vehicleType, ApplicationContext context)
			throws InvalidVehicleType {
		Vehicle vehicle = null;
		switch (vehicleType) {
		case ParkingConstants.SMALL_VEHICLE_TYPE:
			vehicle = (Vehicle) context.getBean("smallVehicle");
			break;
		case ParkingConstants.MEDIUM_VEHICLE_TYPE:
			vehicle = (Vehicle) context.getBean("mediumVehicle");
			break;
		case ParkingConstants.LARGE_VEHICLE_TYPE:
			vehicle = (Vehicle) context.getBean("largeVehicle");
			break;
		default:
			throw new InvalidVehicleType("The vehicle type passed to create a vehicle is not valid.");
		}
		vehicle.setArrivalTime(new Date());
		vehicle.setVehicleNumber(vehicleNumber);
		return vehicle;
	}
}