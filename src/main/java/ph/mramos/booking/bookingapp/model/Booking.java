package ph.mramos.booking.bookingapp.model;

import java.time.ZonedDateTime;

public class Booking {

	private Integer id;
	private ZonedDateTime created;
	private Integer roomCapacity;

	public Booking() {
		super();
	}

	public Booking(Integer id, ZonedDateTime created, Integer roomCapacity) {
		this.id = id;
		this.created = created;
		this.roomCapacity = roomCapacity;
	}

	public Integer getId() {
		return id;
	}

	public ZonedDateTime getCreated() {
		return created;
	}

	public Integer getRoomCapacity() {
		return roomCapacity;
	}

	@Override
	public String toString() {
		return String.format("Booking [id=%s, created=%s, roomCapacity=%s]", id, created, roomCapacity);
	}

}
