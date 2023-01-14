package it.uniroma3.siw.cateringService.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.cateringService.model.Piatto;
import it.uniroma3.siw.cateringService.repository.PiattoRepository;

@Service
public class PiattoService {
	
	@Autowired
	private PiattoRepository piattoRepository;
	
	@Transactional
	public void save(Piatto piatto) {
		piattoRepository.save(piatto);
	}
	
	@Transactional
	public void delete(Piatto piatto) {
		piattoRepository.delete(piatto);
	}
	
	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}
	
	public List<Piatto> findAll(){
		List<Piatto> piatti = new ArrayList<Piatto>();
		for(Piatto p: piattoRepository.findAll()) {
			piatti.add(p);
		}
		return piatti;
	}
	
	public boolean alreadyExists(Piatto piatto) {
		return piattoRepository.existsByNomeAndDescrizione(piatto.getNome(), piatto.getDescrizione());
	}
	
	public void deleteById(Long id) {
		piattoRepository.deleteById(id);
	}
}
