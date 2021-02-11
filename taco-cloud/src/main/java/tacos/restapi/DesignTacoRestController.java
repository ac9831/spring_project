package tacos.restapi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Order;
import tacos.Taco;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;


@RestController
@RequestMapping(path="/rest/design",
				produces="application/json")
@CrossOrigin(origins="*")
public class DesignTacoRestController {
	private TacoRepository tacoRepo;
	private OrderRepository orderRepo;
	
	@Autowired
	EntityLinks entityLink;
	
	public DesignTacoRestController(TacoRepository tacoRepo, OrderRepository orderRepo) {
		this.tacoRepo = tacoRepo;
		this.orderRepo = orderRepo;
	}
	
	@GetMapping("/recent")
	public CollectionModel<EntityModel<Taco>> recentTacos() {
		PageRequest page = PageRequest.of(0,  12, Sort.by("createdAt").descending());
		
		List<Taco> tacos = tacoRepo.findAll(page).getContent();
		CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
		
		recentResources.add(new Link("http://localhost:8080/design/recent", "recents"));
		
		return recentResources;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepo.findById(id);
		if (optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTack(@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}
	
	@PatchMapping(path="/{orderId}", consumes="application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
		
		Order order = orderRepo.findById(orderId).get();
		if (patch.getDeliveryName() != null) {
			order.setDeliveryName(patch.getDeliveryName());
		}
		
		if (patch.getDeliveryStreet() != null) {
			order.setDeliveryStreet(patch.getDeliveryStreet());
		}
		
		if (patch.getDeliveryCity() != null) {
			order.setDeliveryCity(patch.getDeliveryCity());
		}
		
		if (patch.getDeliveryState() != null) {
			order.setDeliveryState(patch.getDeliveryState());
		}
		
		if (patch.getDeliveryZip() != null) {
			order.setDeliveryZip(patch.getDeliveryZip());
		}
		
		if (patch.getCcNumber() != null) {
			order.setCcNumber(patch.getCcNumber());
		}
		
		if (patch.getCcExpiration() != null) {
			order.setCcExpiration(patch.getCcExpiration());
		}
		
		if (patch.getCcCVV() != null) {
			order.setCcCVV(patch.getCcCVV());
		}
		
		return orderRepo.save(order);
	}
	
	@DeleteMapping("/{orderId}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") Long orderId) {
		try {
			orderRepo.deleteById(orderId);
		} catch (EmptyResultDataAccessException e) {}
	}
}
