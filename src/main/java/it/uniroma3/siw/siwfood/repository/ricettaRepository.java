package it.uniroma3.siw.siwfood.repository;

import it.uniroma3.siw.siwfood.entities.User;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.siwfood.entities.ricetta;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ricettaRepository extends CrudRepository<ricetta, Long> {
    ricetta findByTitle(String title);
    List<ricetta> findByAuthor(User user);
}