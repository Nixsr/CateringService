package it.uniroma3.siw.cateringService.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.cateringService.model.Chef;
import it.uniroma3.siw.cateringService.service.ChefService;

@Component
public class ChefValidator implements Validator {

	@Autowired
	private ChefService chefService;
	
	@Override
	public boolean supports(Class<?> cclass) {
		return Chef.class.equals(cclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.chefService.alreadyExists((Chef) target)) {
			errors.reject("chef.duplicato");
		}
		
	}

}
