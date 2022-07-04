package it.uniroma3.siw.cateringService.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.cateringService.controller.validator.IngredienteValidator;
import it.uniroma3.siw.cateringService.model.Ingrediente;
import it.uniroma3.siw.cateringService.service.IngredienteService;
import it.uniroma3.siw.cateringService.service.PiattoService;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private IngredienteValidator validator;
	
	// Metodo POST per inserire un nuovo ingrediente
	
	@PostMapping("/ingrediente")
	public String addBuffet(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		validator.validate(ingrediente, bindingResult);
		if(!bindingResult.hasErrors()) {
			ingredienteService.save(ingrediente);
			model.addAttribute("ingrediente", ingrediente);
			return "ingrediente.html";
		}
		return "ingredienteForm.html";
	}
	
	//Metodo GET per richiedere tutti gli ingredienti
	
	@GetMapping("/ingrediente")
	public String getIngredienti(Model model) {
		List<Ingrediente> ingredienti = ingredienteService.findAll();
		model.addAttribute("ingredienti", ingredienti);
		return "ingredienti.html";
	}
	
	@GetMapping("/ingredienteAdmin")
	public String getIngredientiAdmin(Model model) {
		List<Ingrediente> ingredienti = ingredienteService.findAll();
		model.addAttribute("ingredienti", ingredienti);
		return "ingredientiAdmin.html";
	}
	
	//Metodi GET per eliminare un ingrediente passando come riferimento l'id dello stesso
	
	//Mostra la conferma dell'eliminazione
	
	@GetMapping("/toDeleteIngrediente/{id}")
	public String toDeleteIngrediente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingrediente", ingredienteService.findById((id)));
		return "toDeleteIngrediente.html";
	}
	
	//DELETE dell'ingrediente
	
	@GetMapping("/deleteIngrediente/{id}")
	public String deleteIngrediente(@PathVariable("id") Long id, Model model) {
		ingredienteService.deleteById(id);
		model.addAttribute("ingredienti", ingredienteService.findAll());
		return "ingredienti.html";
	}
	
	//Metodo GET per avere un singolo ingrediente
	
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente.html";
}
	
	@GetMapping("/ingredienteForm")
	public String getIngrediente(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		model.addAttribute("listaPiatti", piattoService.findAll());
		return "ingredienteForm.html";
	}
}