package ph.mramos.booking.bookingapp.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

// No need for @RefreshScope here since configuration properties are refreshed when endpoint /actuator/refresh is triggered.
@ConfigurationProperties(prefix = "app.room")
public class RoomProperties {

	private Integer capacity;

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

}
