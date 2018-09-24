package parking.management.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import parking.management.model.Bus;
import parking.management.model.Car;
import parking.management.model.LargeSpot;
import parking.management.model.MediumSpot;
import parking.management.model.ParkingSpot;
import parking.management.model.SmallSpot;
import parking.management.model.Ticket;
import parking.management.model.TwoWheeler;
import parking.management.model.Valet;
import parking.management.model.Vehicle;
import parking.management.service.ParkingService;

@Configuration
@ComponentScan(basePackageClasses = { parking.management.process.ParkingProcessor.class,
		parking.management.dao.HistoryDao.class, parking.management.dao.ParkingSpotDao.class,
		parking.management.dao.TicketDao.class, parking.management.dao.VehicleDao.class })
@PropertySource(value = { "classpath:application.properties" })
public class ParkingConfiguration {

	@Autowired
	private Environment env;

	@Bean(name = "mySqldataSource")
	public DataSource getMysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.DERBY).addScript("db/sql/create-parkingSystem.sql")
				.addScript("db/sql/insert-parkingSystem.sql").build();
		return db;
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}

	@Bean(name = "valet")
	public ParkingService getValet() {
		return new Valet();
	}

	@Bean(name = "largeParkingSpot")
	public ParkingSpot getLargeParkingSpot() {
		return new LargeSpot();
	}

	@Bean(name = "mediumParkingSpot")
	public ParkingSpot getMediumParkingSpot() {
		return new MediumSpot();
	}

	@Bean(name = "smallParkingSpot")
	public ParkingSpot getSmallParkingSpot() {

		return new SmallSpot();
	}

	@Bean(name = "largeVehicle")
	public Vehicle getBus() {
		return new Bus();
	}

	@Bean(name = "mediumVehicle")
	public Vehicle getCar() {
		return new Car();
	}

	@Bean(name = "smallVehicle")
	public Vehicle getTwoWheeler() {
		return new TwoWheeler();
	}

	@Bean(name = "ticket")
	public Ticket getTicket() {
		return new Ticket();
	}

}
