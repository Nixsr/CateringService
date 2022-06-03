package it.uniroma3.siw.cateringService.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.cateringService.model.Buffet;
import it.uniroma3.siw.cateringService.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;
	
	@Transactional
	public void save(Buffet buffet) {
		buffetRepository.save(buffet);
	}
	
	@Transactional
	public void delete(Buffet buffet) {
		buffetRepository.delete(buffet);
	}
	
	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}
	
	public List<Buffet> findAll(){
		List<Buffet> buffet = new ArrayList<Buffet>();
		for(Buffet b: buffetRepository.findAll()) {
			buffet.add(b);
		}
		return buffet;
	}
	
	public boolean alreadyExists(Buffet buffet) {
		return buffetRepository.existsByNomeAndDescrizione(buffet.getNome(), buffet.getDescrizione());
	}
	
	public void deleteById(Long id) {
		buffetRepository.deleteById(id);
	}
}
