package it.uniroma3.siw.cateringService.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.cateringService.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {

	boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);

}
