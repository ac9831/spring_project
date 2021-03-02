package tacos.restapi;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.Taco;
import tacos.data.TacoRepository;
import tacos.web.api.TacoRepresentationModel;
import tacos.web.api.TacoRepresentationModelAssembler;

@RepositoryRestController
public class RecentTacosController {

	private TacoRepository tacoRepo;
	
	public RecentTacosController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}
	
	@GetMapping(path="/tacos/recent", produces="application/hal+json")
	public ResponseEntity<CollectionModel<TacoRepresentationModel>> recentTacos() {
		PageRequest page = PageRequest.of(0,  12, Sort.by("createdAt").descending());
		List<Taco> tacos = tacoRepo.findAll(page).getContent();
		
		CollectionModel<TacoRepresentationModel> recentCollectionModel = new TacoRepresentationModelAssembler().toCollectionModel(tacos);
		recentCollectionModel.add(linkTo(methodOn(RecentTacosController.class).recentTacos()).withRel("recents"));
		
		return new ResponseEntity<>(recentCollectionModel, HttpStatus.OK);
	}
	
	@Bean
	public RepresentationModelProcessor<PagedModel<CollectionModel<Taco>>> tacoProcessor(EntityLinks links) {
		return new RepresentationModelProcessor<PagedModel<CollectionModel<Taco>>>() {
			@Override
			public PagedModel<CollectionModel<Taco>> process(PagedModel<CollectionModel<Taco>> models) {
				
				models.add(links.linkFor(Taco.class).slash("recent").withRel("recents"));
				return models;
			}
		};
	}
}
