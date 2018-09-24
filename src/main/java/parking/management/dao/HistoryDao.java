package parking.management.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import parking.management.model.Ticket;
import parking.management.model.Vehicle;

@Repository
@Qualifier("historyDao")
public class HistoryDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void insertTicketHistory(Ticket ticket) {

		String query = "insert into ticket_history (id, vehicle_id, parking_spot_id, arrival_time, "
				+ "departure_time) values (?, ?, ?, ?, ?)";
		jdbcTemplate.update(query, ticket.getId(), ticket.getVehicleId(), ticket.getParkingSpotId(),
				ticket.getArrivalTime(), new Date());
	}

	public void insertVehicleHistory(Vehicle vehicle) {

		String query = "insert into vehicle_history (id, vehicle_type, vehicle_number, arrival_time, "
				+ "departure_time) values (?, ?, ?, ?, ?)";
		jdbcTemplate.update(query, vehicle.getId(), vehicle.getVehicleType(), vehicle.getVehicleNumber(),
				vehicle.getArrivalTime(), new Date());
	}

	public void clearOldHistoryData(Date date) {
		jdbcTemplate.update("delete from ticket_history where arrival_time < = ? ", date);
		jdbcTemplate.update("delete from vehicle_history where arrival_time < = ? ", date);
	}

	public void CleanUp() {
		String query = " insert into ticket_history select * from ticket where departure_time is not null";
		jdbcTemplate.update(query);
		query = "insert into vehicle_history select * from vehicle where departure_time is not null";
		jdbcTemplate.update(query);
	}
}
