package parking.management.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import parking.management.constant.ParkingConstants;
import parking.management.exception.InvalidParking;
import parking.management.model.Bus;
import parking.management.model.Car;
import parking.management.model.TwoWheeler;
import parking.management.model.Vehicle;

@Repository
@Qualifier("vehicleDao")
public class VehicleDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Vehicle addVehicle(Vehicle vehicle) throws InvalidParking {
		String query = "insert into vehicle (id, vehicle_type,vehicle_number,arrival_time,departure_time) "
				+ "values (next value FOR  vehicle_id_seq, ?, ?, ?, ?)";
		int addCount = jdbcTemplate.update(query, vehicle.getVehicleType(), vehicle.getVehicleNumber(), new Date(),
				null);
		if (addCount > 0) {
			return getVehicle(vehicle.getVehicleType(), vehicle.getVehicleNumber());
		} else {
			throw new InvalidParking("Vehicle of type " + vehicle.getVehicleType() + " and number : "
					+ vehicle.getVehicleNumber() + " is not present");
		}
	}

	public Vehicle getVehicle(String vehicleType, String vehicleNumber) {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();

		String query = "select  * from vehicle where vehicle_type = ? and vehicle_number = ?";

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, vehicleType, vehicleNumber);
		for (Map<?, ?> row : rows) {
			Vehicle vehicle;
			vehicleType = (String) row.get("vehicle_type");
			if (vehicleType.equalsIgnoreCase(ParkingConstants.SMALL_VEHICLE_TYPE)) {
				vehicle = new TwoWheeler();
			} else if (vehicleType.equalsIgnoreCase(ParkingConstants.MEDIUM_VEHICLE_TYPE)) {
				vehicle = new Car();
			} else {
				vehicle = new Bus();
			}
			vehicle.setId((int) (row.get("id")));
			vehicle.setVehicleNumber((String) row.get("vehicle_number"));
			vehicle.setArrivalTime((Date) row.get("arrival_time"));
			vehicle.setDepartureTime((Date) row.get("departure_time"));
			vehicles.add(vehicle);
		}
		System.out.println(vehicles.get(0).toString());
		return vehicles.get(0);
	}

	public Vehicle getVehicle(int id) {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		String query = "select  * from vehicle where id = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, 1);
		for (Map<?, ?> row : rows) {
			Vehicle vehicle;
			String vehicleType = (String) row.get("vehicle_type");
			if (vehicleType.equalsIgnoreCase(ParkingConstants.SMALL_VEHICLE_TYPE)) {
				vehicle = new TwoWheeler();
			} else if (vehicleType.equalsIgnoreCase(ParkingConstants.MEDIUM_VEHICLE_TYPE)) {
				vehicle = new Car();
			} else {
				vehicle = new Bus();
			}
			vehicle.setId((int) (row.get("id")));
			vehicle.setVehicleNumber((String) row.get("vehicle_number"));
			vehicle.setArrivalTime((Date) row.get("arrival_time"));
			vehicle.setDepartureTime((Date) row.get("departure_time"));
			vehicles.add(vehicle);
		}
		return vehicles.get(0);

	}

	public void deleteVehicle(Vehicle vehicle) {
		jdbcTemplate.update("delete from vehicle where id = ?", vehicle.getId());

	}

	public void detachVehicle(Vehicle vehicle) {
		jdbcTemplate.update("update vehicle set departure_time = CURRENT TIMESTAMP where id = ?", vehicle.getId());
	}

	public void cleaUp() {
		jdbcTemplate.update(
				"delete from vehicle where departure_time is not null and id not in (select distinct vehicle_id from ticket)");

	}
}
