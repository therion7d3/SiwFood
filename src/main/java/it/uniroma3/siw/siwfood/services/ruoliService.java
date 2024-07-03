package it.uniroma3.siw.siwfood.services;

import it.uniroma3.siw.siwfood.entities.ruolo;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwfood.repository.ruoliRepository;


import java.util.List;

@Service
public class ruoliService {

    private final ruoliRepository ruoliRepository;

    public ruoliService(ruoliRepository ruoliRepository) {
        this.ruoliRepository = ruoliRepository;
    }

    public List<ruolo> getAllRuoli() {
        return (List<ruolo>) ruoliRepository.findAll();
    }

    public ruolo getRuoloById(Long id) {
        return ruoliRepository.findById(id).orElse(null);
    }

    public ruolo salvaRuolo(ruolo ruolo) {
        return ruoliRepository.save(ruolo);
    }
}
