package ph.mramos.booking.bookingapp.controller;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ph.mramos.booking.bookingapp.model.Booking;

@RestController
@RequestMapping("/booking")
public class BookingController {

	private final Map<Integer, Booking> BOOKING_MAP = new ConcurrentHashMap<>();
	private final LongAdder ID_INCREMENTER = new LongAdder();

	@Value("${app.room.capacity}")
	private Integer roomCapacity;

	@Autowired
	private DiscoveryClient discoveryClient;

	@PostConstruct
	private void init() {
		for (int i = 0; i < 2; i++) {
			ID_INCREMENTER.increment();
			int id = ID_INCREMENTER.intValue();
			BOOKING_MAP.put(id, new Booking(id, ZonedDateTime.now(), roomCapacity));
		}
	}

	@GetMapping
	public List<Booking> getAll() {
		List<String> serviceIds = discoveryClient.getServices();
		serviceIds.forEach(System.out::println);
		System.out.println();

		for (String serviceId : serviceIds) {
			List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);

			for (ServiceInstance serviceInstance : serviceInstances) {
				System.out.println("Scheme: " + serviceInstance.getScheme());
				System.out.println("Host: " + serviceInstance.getHost());
				System.out.println("Port: " + serviceInstance.getPort());
				System.out.println("URI: " + serviceInstance.getUri());
				System.out.println("Service ID: " + serviceInstance.getServiceId());
				System.out.println("Instance ID: " + serviceInstance.getInstanceId());
				System.out.println("Secure: " + serviceInstance.isSecure());
				System.out.println("Metadata: " + serviceInstance.getMetadata());
				System.out.println();
			}
		}

		return BOOKING_MAP.values().stream().sorted(Comparator.comparing(Booking::getId)).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Booking> get(@PathVariable Integer id) {
		if (!BOOKING_MAP.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}

		Booking booking = BOOKING_MAP.get(id);
		return ResponseEntity.ok(booking);
	}

}
