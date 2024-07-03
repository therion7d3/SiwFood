package it.uniroma3.siw.siwfood.services;

import it.uniroma3.siw.siwfood.entities.ingrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwfood.repository.ingredientiRepository;

import java.util.List;

@Service
public class ingredentiService {

    @Autowired
    private ingredientiRepository ingredientiRepository;

    public ingrediente salvaNuovoIngrediente(ingrediente ingrediente) {
        return ingredientiRepository.save(ingrediente);
    }

    public ingrediente getIngredienteById(Long id) {
        return ingredientiRepository.findById(id).orElse(null);
    }

    public List<ingrediente> getTuttiIngredienti() {
        return ingredientiRepository.findAll();
    }

}
