package it.uniroma3.siw.siwfood.services;

import it.uniroma3.siw.siwfood.entities.ricetta;
import it.uniroma3.siw.siwfood.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ricettaService {

    @Autowired
    private it.uniroma3.siw.siwfood.repository.ricettaRepository ricettaRepository;

    public List<ricetta> getRicetteByChef(User user) {
        List<ricetta> listaRicette = (List<ricetta>)ricettaRepository.findByAuthor(user);
        return listaRicette;
    }

    public ricetta salvaRicetta(ricetta ricetta) {
        return ricettaRepository.save(ricetta);
    }

    public List<ricetta> getTutteRicette() {
        return (List<ricetta>) ricettaRepository.findAll();
    }

    public ricetta getRicettaById(Long id) {
        return ricettaRepository.findById(id).orElse(null);
    }

    public void eliminaRicettaById(long id) {
        ricettaRepository.deleteById(id);
    }

    public boolean eliminaRicettaChef(Long ricettaId, User user) {
        ricetta ricetta = ricettaRepository.findById(ricettaId).orElse(null);
        if (ricetta != null && ricetta.getAuthor().equals(user)) {
            ricettaRepository.deleteById(ricettaId);
            return true;
        }
        return false;
    }

    public ricetta getRicettaByNome(String name) {
        return ricettaRepository.findByTitle(name);
    }
}
