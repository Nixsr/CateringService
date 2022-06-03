package it.uniroma3.siw.cateringService.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.cateringService.model.Chef;
import it.uniroma3.siw.cateringService.repository.ChefRepository;

@Service
public class ChefService {
	
	@Autowired
	private ChefRepository chefRepository;
	
	@Transactional
	public void save(Chef chef) {
		chefRepository.save(chef);
	}
	
	@Transactional
	public void delete(Chef chef) {
		chefRepository.delete(chef);
	}
	
	public Chef findById(Long id) {
		return chefRepository.findById(id).get();
	}
	
	public List<Chef> findAll(){
		List<Chef> chefs = new ArrayList<Chef>();
		for(Chef c: chefRepository.findAll()) {
			chefs.add(c);
		}
		return chefs;
	}
	
	public boolean alreadyExists(Chef chef) {
		return chefRepository.existsByNomeAndCognomeAndNazionalita(chef.getNome(), chef.getCognome(), chef.getNazionalita());
	}
	
	public void deleteById(Long id) {
		chefRepository.deleteById(id);
	}
}