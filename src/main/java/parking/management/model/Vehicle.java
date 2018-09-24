package parking.management.model;

import java.util.Date;

public abstract class Vehicle {
	private int id;
	private String vehicleNumber;
	private Date arrivalTime;
	private Date departureTime;

	public Vehicle() {

	}

	public Vehicle(String vehicleNumber, Date arrivalTime) {
		this.id = 0;
		this.vehicleNumber = vehicleNumber;
		this.arrivalTime = new Date(arrivalTime.getTime());
		this.departureTime = null;
	}

	public int getId() {
		return id;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public abstract String getVehicleType();

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("Id - " + this.getId() + "\nNumber - " + this.getVehicleNumber() + "\nvehicle type - "
				+ this.getVehicleType());
		sb.append("\narrival time - " + this.getArrivalTime());
		if(null != this.getDepartureTime()) {
			sb.append("\n departure time - " + this.getDepartureTime());
		}
		return sb.toString();
	}
}