package it.uniroma3.siw.siwfood.repository;

import it.uniroma3.siw.siwfood.entities.ruolo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ruoliRepository extends CrudRepository<ruolo, Long> {
    Optional<ruolo> findById(Long id);
}