package parking.management.model;

public abstract class ParkingSpot {
	private int id;
	private String parkingSpotNumber;
	private ParkingLot parkingLot;
	private boolean occupied;

	public ParkingSpot(int id, String spotNum, ParkingLot lot, boolean occupied) {
		this.id = id;
		this.parkingSpotNumber = spotNum;
		this.parkingLot = lot;
		this.occupied = occupied;
	}

	public ParkingSpot(int id, String spotNum, String lot, boolean occupied) {
		this.id = id;
		this.parkingSpotNumber = spotNum;
		switch (lot.toLowerCase()) {
		case "compact":
			this.parkingLot = ParkingLot.COMPACT;
			break;
		case "handicapped":
			this.parkingLot = ParkingLot.HANDICAPPED;
			break;

		default: this.parkingLot = ParkingLot.REGULAR;
			break;
		}

		this.occupied = occupied;
	}

	public ParkingSpot() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getParkingSpotNumber() {
		return parkingSpotNumber;
	}

	public void setParkingSpotNumber(String parkingSpotNumber) {
		this.parkingSpotNumber = parkingSpotNumber;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	public void setParkingLot(String parkingLot) {
		switch (parkingLot.toLowerCase()) {
		case "compact":
			this.parkingLot = ParkingLot.COMPACT;
			break;
		case "handicapped":
			this.parkingLot = ParkingLot.HANDICAPPED;
			break;

		default: this.parkingLot = ParkingLot.REGULAR;
			break;
		}
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public abstract String getSpotType();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("\nParking spot details \nid :  " + this.id + " spot number = " + this.parkingSpotNumber);
		sb.append("\nParking type :  " + this.parkingLot.name().toLowerCase() + "\nOccupied : " + this.occupied);
		return sb.toString();
	}
}