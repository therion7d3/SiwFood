package it.uniroma3.siw.siwfood.repository;
import it.uniroma3.siw.siwfood.entities.foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface fotoRepository extends JpaRepository<foto, Long> {
    Optional<foto> findByUserId(Long userId);
    Optional<foto> findByRicettaId(Long ricettaId);
}
