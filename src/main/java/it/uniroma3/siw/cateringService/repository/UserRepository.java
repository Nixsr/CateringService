package it.uniroma3.siw.cateringService.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.cateringService.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
