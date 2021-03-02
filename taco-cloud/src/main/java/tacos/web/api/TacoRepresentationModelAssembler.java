package tacos.web.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import tacos.Taco;
import tacos.restapi.DesignTacoRestController;

public class TacoRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Taco, TacoRepresentationModel>{

	public TacoRepresentationModelAssembler(Class<?> controllerClass, Class<TacoRepresentationModel> resourceType) {
		super(controllerClass, resourceType);
	}

	public TacoRepresentationModelAssembler() {
        super(DesignTacoRestController.class, TacoRepresentationModel.class);
    }

	@Override
	public TacoRepresentationModel toModel(Taco taco) {
		return createModelWithId(taco.getId(), taco);
	}
	

}
