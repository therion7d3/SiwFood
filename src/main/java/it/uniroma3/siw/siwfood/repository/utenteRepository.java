package it.uniroma3.siw.siwfood.repository;

import it.uniroma3.siw.siwfood.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface utenteRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}