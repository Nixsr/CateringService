package it.uniroma3.siw.cateringService.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.cateringService.model.Ingrediente;
import it.uniroma3.siw.cateringService.service.IngredienteService;

@Component
public class IngredienteValidator implements Validator{

	@Autowired
	private IngredienteService ingredienteService;
	
	@Override
	public boolean supports(Class<?> iclass) {
		return Ingrediente.class.equals(iclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.ingredienteService.alreadyExists((Ingrediente) target)) {
			errors.reject("ingredientes.duplicato");
		}
		
	}
}
