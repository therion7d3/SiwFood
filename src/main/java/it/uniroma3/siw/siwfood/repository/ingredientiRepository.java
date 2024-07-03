package it.uniroma3.siw.siwfood.repository;

import it.uniroma3.siw.siwfood.entities.ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ingredientiRepository extends JpaRepository<ingrediente, Long> {
}
