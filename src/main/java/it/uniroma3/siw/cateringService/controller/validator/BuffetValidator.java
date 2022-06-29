package it.uniroma3.siw.cateringService.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.cateringService.model.Buffet;
import it.uniroma3.siw.cateringService.service.BuffetService;

@Component
public class BuffetValidator implements Validator {

	@Autowired
	private BuffetService buffetService;
	
	@Override
	public boolean supports(Class<?> bclass) {
		return Buffet.class.equals(bclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.buffetService.alreadyExists((Buffet) target)) {
			errors.reject("buffet.duplicato");
		}
		
	}
	
	public void validateUpdate(Object o, Errors errors) {

		//Verifico la presenza di spazi o di stringa vuota

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "required");
    }

}
