package it.uniroma3.siw.cateringService.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.cateringService.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {

	boolean existsByNomeAndDescrizione(String nome, String descrizione);

}
