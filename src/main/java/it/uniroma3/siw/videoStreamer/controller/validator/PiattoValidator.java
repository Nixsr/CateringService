package it.uniroma3.siw.cateringService.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.cateringService.model.Piatto;
import it.uniroma3.siw.cateringService.service.PiattoService;

@Component
public class PiattoValidator implements Validator {

	@Autowired
	private PiattoService piattoService;
	
	@Override
	public boolean supports(Class<?> pclass) {
		return Piatto.class.equals(pclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.piattoService.alreadyExists((Piatto) target)) {
			errors.reject("piatto.duplicato");
		}
		
	}

}
