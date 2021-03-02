package tacos.web.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import tacos.Ingredient;
import tacos.restapi.DesignTacoRestController;

public class IngredientRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientRepresentationModel>{

	public IngredientRepresentationModelAssembler(Class<?> controllerClass,	Class<IngredientRepresentationModel> resourceType) {
		super(controllerClass, resourceType);
	}
	
	public IngredientRepresentationModelAssembler() {
		super(DesignTacoRestController.class, IngredientRepresentationModel.class);
	}

	@Override
	public IngredientRepresentationModel toModel(Ingredient ingredient) {
		return createModelWithId(ingredient.getId(), ingredient);
	}

}
