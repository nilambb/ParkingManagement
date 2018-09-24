package parking.management.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import parking.management.exception.InvalidParking;
import parking.management.exception.InvalidTicket;
import parking.management.model.Ticket;

@Repository
@Qualifier("ticketDao")
public class TicketDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Ticket createTicket(int vehicleId, int parkingSpotId) throws InvalidParking {
		String query = "insert into ticket(id,vehicle_id, parking_spot_id, arrival_time, departure_time) "
				+ "values (next value FOR ticket_id_seq, ?,?,?,?)";
		System.out.println("inserting tickets - ParkingSpot = " + parkingSpotId + " and vehicleId = " + vehicleId);
		int ticketCount = jdbcTemplate.update(query, vehicleId, parkingSpotId, new Date(), null);
		if (ticketCount > 0) {
			return getTicket(vehicleId, parkingSpotId);
		} else {
			throw new InvalidParking("Sorry can not create ticket....");
		}
	}

	public Ticket getTicket(int vehicleId, int parkingSpotId) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		String query = "select * from ticket where vehicle_id = ? and parking_spot_id = ? and departure_time is null";
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, vehicleId, parkingSpotId);
		for (Map<?, ?> row : rows) {
			Ticket ticket = new Ticket();
			ticket.setId((int) (row.get("id")));
			ticket.setVehicleId((int) row.get("vehicle_id"));
			ticket.setParkingSpotId((int) row.get("parking_spot_id"));
			ticket.setArrivalTime((Date) row.get("arrival_time"));
			ticket.setDepartureTime((Date) row.get("departure_time"));
			tickets.add(ticket);
		}
		System.out.println(tickets.get(0).toString());
		return tickets.get(0);
	}

	public Ticket getTicket(int ticketId) {
		Ticket ticket = null;
		List<Ticket> tickets = new ArrayList<Ticket>();
		String query = "select * from ticket where id = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, ticketId);
		for (Map<?, ?> row : rows) {
			ticket = new Ticket();
			ticket.setId((int) (row.get("id")));
			ticket.setVehicleId((int) row.get("vehicle_id"));
			ticket.setParkingSpotId((int) row.get("parking_spot_id"));
			ticket.setArrivalTime((Date) row.get("arrival_time"));
			ticket.setDepartureTime((Date) row.get("departure_time"));
			tickets.add(ticket);
		}
		System.out.println(tickets.get(0).toString());
		return tickets.get(0);
	}

	public boolean isTicketExists(Ticket ticket) {
		int ticketCount = 0;

		String query = "select count(*) from ticket where id = ? and vehicle_id = ? and parking_spot_id = ? and departure_time is null";
		Number number = jdbcTemplate.queryForObject(query,
				new Object[] { ticket.getId(), ticket.getVehicleId(), ticket.getParkingSpotId() }, Number.class);
		ticketCount = (number != null ? number.intValue() : 0);

		if (ticketCount > 0) {
			return true;
		} else {
			return false;
		}

	}

	public void deleteTicket(Ticket ticket) throws InvalidTicket {
		int count = jdbcTemplate.update("delete from ticket where ticket_id = ?", ticket.getId());
		if (count > 0) {
			throw new InvalidTicket("Invalid ticket can not delete the ticktet " + ticket.toString());
		}
	}

	public boolean isValidTicket(int ticketId) {
		int ticketCount = 0;

		String query = "select count(*) from ticket t, vehicle v, parking_spot ps where "
				+ " t.vehicle_id = v.id and t.parking_spot_id = ps.id and t.id = ? and "
				+ "  ps.occupied = ? and t.departure_time is null and v.departure_time is null";

		Number number = jdbcTemplate.queryForObject(query,
				new Object[] { ticketId, "Y" }, Number.class);
		ticketCount = (number != null ? number.intValue() : 0);

		if (ticketCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void deallocateTicket(Ticket ticket) {
		jdbcTemplate.update("update ticket set departure_time = CURRENT TIMESTAMP where id = ?", ticket.getId());
	}

	public void cleanUp() {
		jdbcTemplate.update("delete from ticket where departure_time is not null ");
	}

}
