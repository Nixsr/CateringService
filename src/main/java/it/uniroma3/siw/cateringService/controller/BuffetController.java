package it.uniroma3.siw.cateringService.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.cateringService.controller.validator.BuffetValidator;
import it.uniroma3.siw.cateringService.model.Buffet;
import it.uniroma3.siw.cateringService.model.Chef;
import it.uniroma3.siw.cateringService.model.Piatto;
import it.uniroma3.siw.cateringService.service.BuffetService;
import it.uniroma3.siw.cateringService.service.ChefService;
import it.uniroma3.siw.cateringService.service.PiattoService;

@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetValidator validator;
	
	@Autowired
	private PiattoService piattoService;
	
	@PostMapping("/buffet")
	public String addBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult, Model model) {
		validator.validate(buffet, bindingResult);
		if(!bindingResult.hasErrors()) {
			buffetService.save(buffet);
			model.addAttribute("buffet", buffet);
			return "buffet.html";
		}
		return "admin/buffetForm.html";
	}
	
	@GetMapping("/buffet")
	public String getBuffetList(Model model) {
		List<Buffet> buffet = buffetService.findAll();
		model.addAttribute("buffetList", buffet);
		return "buffetList.html";
	}
	
	@GetMapping("/buffetAdmin")
	public String getBuffetAdminList(Model model) {
		List<Buffet> buffet = buffetService.findAll();
		model.addAttribute("buffetList", buffet);
		return "buffetListAdmin.html";
	}
	
	
	@GetMapping("/toDeleteBuffet/{id}")
	public String toDeleteBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", buffetService.findById((id)));
		return "toDeleteBuffet.html";
	}
	
	@GetMapping("/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		buffetService.deleteById(id);
		model.addAttribute("buffetList", buffetService.findAll());
		return "buffetList.html";
	}
	
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "buffet.html";
}
	
	@GetMapping("/buffetForm")
	public String getBuffet(Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("listaChef", chefService.findAll());
		
		model.addAttribute("piatti", piattoService.findAll());
		return "admin/buffetForm.html";
	}
	
	@GetMapping("/editBuffet/{id}")
	public String editBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", buffetService.findById(id));
		model.addAttribute("chefs", chefService.findAll());
		model.addAttribute("dishes", this.piattoService.findAll());
		return "editBuffet";
	}

	@PostMapping("/editBuffet/{id}")
	public String saveEditedBuffet(@PathVariable("id") Long id, 
									@RequestParam("piatti") List<Piatto> piatti,
									@ModelAttribute("buffet") Buffet buffet , Model model) {
		Buffet originalBuffet = buffetService.findById(id);

		originalBuffet.setId(buffet.getId());
		originalBuffet.setNome(buffet.getNome());
		originalBuffet.setDescrizione(buffet.getDescrizione());
		originalBuffet.getChef().getBuffet().remove(originalBuffet);
		originalBuffet.setChef(buffet.getChef());
		originalBuffet.setPiatti(piatti);

		buffetService.save(originalBuffet);

		model.addAttribute("buffets", buffetService.findAll());

		return "buffetList";
	}
	
}