package parking.management.model;

import java.util.Date;

public class Ticket {
	private int id;
	private int vehicleId;
	private int parkingSpotId;
	private Date arrivalTime;
	private Date departureTime;

	public Ticket(int id, int vehicleId, int parkingSpotId, Date arrivaltime, Date departureTime) {
		this.id = id;
		this.vehicleId = vehicleId;
		this.parkingSpotId = parkingSpotId;
		this.arrivalTime = new Date(arrivaltime.getTime());
		this.departureTime = new Date(departureTime.getTime());
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void setParkingSpotId(int parkingSpotId) {
		this.parkingSpotId = parkingSpotId;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Ticket() {
		
	}
	public int getId() {
		return id;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public int getParkingSpotId() {
		return parkingSpotId;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	@Override
	public String toString() {
		StringBuilder ticketInfo = new StringBuilder(200);
		ticketInfo.append("Ticket Number = " + this.id);
		ticketInfo.append("\nParking spot number = " + this.parkingSpotId + "\nVehcile id " + this.vehicleId);
		ticketInfo.append("\nArrivalTime = " + this.arrivalTime + "\nDeparture Time = " + this.departureTime);
		return ticketInfo.toString();
	}

}
