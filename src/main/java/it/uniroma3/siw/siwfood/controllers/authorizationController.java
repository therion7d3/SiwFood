package it.uniroma3.siw.siwfood.controllers;

import it.uniroma3.siw.siwfood.exceptions.authException;
import it.uniroma3.siw.siwfood.entities.ruolo;
import it.uniroma3.siw.siwfood.entities.User;
import it.uniroma3.siw.siwfood.repository.utenteRepository;
import it.uniroma3.siw.siwfood.responses.authResponse;
import it.uniroma3.siw.siwfood.services.JWTService;
import it.uniroma3.siw.siwfood.services.ruoliService;
import it.uniroma3.siw.siwfood.services.utenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class authorizationController {

    private final utenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtTokenService;
    private final utenteService utenteService;
    private final ruoliService ruoliService;

    @Autowired
    public authorizationController(utenteRepository utenteRepository, PasswordEncoder passwordEncoder, JWTService jwtTokenService, utenteService utenteService, ruoliService ruoliService) {

        this.jwtTokenService = jwtTokenService;
        this.utenteRepository = utenteRepository;
        this.utenteService = utenteService;
        this.passwordEncoder = passwordEncoder;
        this.ruoliService = ruoliService;
    }

    @PostMapping("/login")
    public authResponse login(@RequestBody loginFormat loginFormat) {
        String email = loginFormat.getUsername();
        String password = loginFormat.getPassword();
        User utente = utenteRepository.findByEmail(email);
        if (utente != null) {
            if (passwordEncoder.matches(password, utente.getPassword())) {
                //aggiungo i claims da inserire all'interno del token
                Map<String, Object> claims = new HashMap<>();
                claims.put("ruoli", utente.getRoleList());
                /*
                Generazione del JWT <--> Chiamo il Builder e gli passo dei claims custom (username)
                 */
                String token = jwtTokenService.generateToken(claims, utente);
                return new authResponse(token, "Autenticazione completata con successo.", true, utente.getRoleList(), utente.getNome(), utente.getId());
            } else throw new authException("Le credenziali inserite sono errate.", HttpStatus.BAD_REQUEST);
        }

        throw new authException("Le credenziali inserite sono errate.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registrationFormat registrationFormat) {
        try {

            User user = new User();
            user.setEmail(registrationFormat.getEmail());
            user.setNome(registrationFormat.getNome());
            user.setCognome(registrationFormat.getCognome());
            user.setDob(registrationFormat.getDob());
            user.setPassword(passwordEncoder.encode(registrationFormat.getPassword()));
            List<ruolo> ruolos = new ArrayList<>();
            ruolos.add(ruoliService.getRuoloById(1L));
            user.setRoleList(ruolos);
            utenteService.registerUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

