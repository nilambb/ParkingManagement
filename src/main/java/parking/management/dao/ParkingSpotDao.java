package parking.management.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import parking.management.exception.InvalidParking;
import parking.management.exception.InvalidParkingType;
import parking.management.exception.InvalidVehicleType;

@Repository
@Qualifier("parkingSpotDao")
public class ParkingSpotDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	// There should not be any vehicle parked.
	public boolean isEmpty() {
		Integer number = jdbcTemplate.queryForObject("select count(1) from parking_spot where occupied = ? ",
				new Object[] { "Y" }, Integer.class);
		number = number != null ? number.intValue() : 0;

		if (number > 0) {
			return false;
		}
		return true;
	}

	// At least one parking spot is available
	public boolean isFull() {
		Integer number = jdbcTemplate.queryForObject("select count(1) from parking_spot where occupied = ? ",
				new Object[] { "N" }, Integer.class);
		number = number != null ? number.intValue() : 0;
		if (number > 0) {
			return false;
		}
		return true;
	}

	public int getParkingSpot(String parkingType, String spotType) {
		String query = "select min(id) from parking_spot " + "where parking_spot_type = ? and "
				+ " parking_type = ? and occupied = ? ";

		Number number = jdbcTemplate.queryForObject(query,
				new Object[] { spotType.toLowerCase(), parkingType.toLowerCase(), "N" }, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public boolean isParkingSpotAvailable(String parkingType, String spotType) {
		int parkingSpot = 0;

		String query = "select count(*) from parking_spot " + " where parking_spot_type = ? and "
				+ " parking_type = ? and occupied = ? ";

		Number number = jdbcTemplate.queryForObject(query,
				new Object[] { spotType.toLowerCase(), parkingType.toLowerCase(), "N" }, Integer.class);
		parkingSpot = (number != null ? number.intValue() : 0);
		if (parkingSpot > 0) {
			return true;
		}
		return false;
	}

	public void allocateParkingSpot(int parkingSpotId) throws InvalidParking {
		int updateCount = jdbcTemplate.update("update parking_spot set occupied = ? where id = ? ", "Y", parkingSpotId);
		if (updateCount <= 0) {
			throw new InvalidParking(
					"Invalid parking spot. Can not allocate the parking spot as specied spot does not exist \n Spot Id = "
							+ parkingSpotId);
		}
	}

	public void deallocateParkingSpot(int parkingSpotId) throws InvalidParking {
		int updateCount = jdbcTemplate.update("update parking_spot set occupied = ? where id = ? ", "N", parkingSpotId);
		if (updateCount <= 0) {
			throw new InvalidParking(
					"Invalid parking spot. Can not allocate the parking spot as specied spot does not exist \n Spot Id = "
							+ parkingSpotId);
		}
	}

	public boolean isParkingAvailable(String parkingType, String vehicleType)
			throws InvalidVehicleType, InvalidParkingType {
		String query = "select count(*) from parking_spot where parking_type = ? "
				+ " and parking_spot_type in (select spot_type from parking_spcification where vehicle_type = ?) "
				+ " and occupied = ?";

		Integer number = jdbcTemplate.queryForObject(query,
				new Object[] { parkingType.toLowerCase(), vehicleType.toLowerCase(), "N" }, Integer.class);
		number = (number != null ? number.intValue() : 0);

		if (number > 0) {
			return true;
		}
		return false;
	}

}
