package tacos.web.api;

import java.util.Date;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import tacos.Taco;

@Relation(value="taco", collectionRelation="tacos")
public class TacoRepresentationModel extends RepresentationModel<TacoRepresentationModel> {

	private static final IngredientRepresentationModelAssembler ingredientAssembler = new IngredientRepresentationModelAssembler();
	
	@Getter
	private final String name;
	
	@Getter
	private final Date createdAt;
	
	@Getter
	private final CollectionModel<IngredientRepresentationModel> ingredients;
	
	public TacoRepresentationModel(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
		this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
	}
}
