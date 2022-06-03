package it.uniroma3.siw.cateringService.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.cateringService.controller.validator.PiattoValidator;
import it.uniroma3.siw.cateringService.model.Ingrediente;
import it.uniroma3.siw.cateringService.model.Piatto;
import it.uniroma3.siw.cateringService.service.PiattoService;

public class PiattoController {
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private PiattoValidator validator;
	
	// Metodo POST per inserire un nuovo piatto
	
	@PostMapping("/piatto")
	public String addPiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		validator.validate(piatto, bindingResult);
		if(!bindingResult.hasErrors()) {
			piattoService.save(piatto);
			model.addAttribute("piatto", piatto);
			return "piatto.html";
		}
		return "ingredienteForm.html";
	}
	
	//Metodo GET per richiedere tutti i piatti
	
	@GetMapping("/piatto")
	public String getPiatti(Model model) {
		List<Piatto> piatti = piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return "piatti.html";
	}
	
	//Metodi GET per eliminare un piatto passando come riferimento l'id dello stesso
	
	//Mostra la conferma dell'eliminazione
	
	@GetMapping("/toDeletePiatto/{id}")
	public String toDeletePiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", piattoService.findById((id)));
		return "toDeletePiatto.html";
	}
	
	//DELETE del piatto
	
	@GetMapping("/deletePiatto/{id}")
	public String deletePiatto(@PathVariable("id") Long id, Model model) {
		piattoService.deleteById(id);
		model.addAttribute("piatti", piattoService.findAll());
		return "piatti.html";
	}
	
	//Metodo GET per avere un singolo piatto
	
	@GetMapping("/piatto/{id}")
	public String getPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		return "piatto.html";
}
	
	@GetMapping("/piattoForm")
	public String getPiatto(Model model) {
		model.addAttribute("piatto", new Ingrediente());
		return "piattoForm.html";
	}
}