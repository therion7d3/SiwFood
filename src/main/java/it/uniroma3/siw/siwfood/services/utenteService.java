package it.uniroma3.siw.siwfood.services;

import it.uniroma3.siw.siwfood.exceptions.authException;
import it.uniroma3.siw.siwfood.entities.User;
import it.uniroma3.siw.siwfood.repository.utenteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class utenteService implements UserDetailsService {

    private final utenteRepository repository;

    public utenteService(utenteRepository repository) {
        this.repository = repository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new authException("Utente non trovato con l'email: " + email, HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public UserDetails loadById(Long id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new authException("Utente non trovato con id: " + id, HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public void deleteById(Long id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new authException("Utente non trovato con id: " + id, HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public void registerUser(User user) {
        repository.save(user);
    }

    public List<User> getAllUsers(){
        return (List<User>) repository.findAll();
    }
}
