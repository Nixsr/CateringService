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

import it.uniroma3.siw.cateringService.controller.validator.ChefValidator;
import it.uniroma3.siw.cateringService.model.Buffet;
import it.uniroma3.siw.cateringService.model.Chef;
import it.uniroma3.siw.cateringService.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;
	
	@Autowired
	private ChefValidator validator;
	
	@PostMapping("/chef")
	public String addBuffet(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
		validator.validate(chef, bindingResult);
		if(!bindingResult.hasErrors()) {
			chefService.save(chef);
			model.addAttribute("chef", chef);
			return "chef.html";
		}
		return "chefForm.html";
	}
	
	@GetMapping("/chef")
	public String getBuffetList(Model model) {
		List<Chef> chef = chefService.findAll();
		model.addAttribute("chefs", chef);
		return "chefs.html";
	}
	
	@GetMapping("/toDeleteChef/{id}")
	public String toDeleteChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById((id)));
		return "toDeleteChef.html";
	}
	
	@GetMapping("/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		chefService.deleteById(id);
		model.addAttribute("chefs", chefService.findAll());
		return "chefs.html";
	}
	
	@GetMapping("/chef/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		return "chef.html";
}
	
	@GetMapping("/chefForm")
	public String getBuffet(Model model) {
		model.addAttribute("chef", new Chef());
		return "chefForm.html";
	}
	
}
