package it.uniroma3.siw.siwfood.controllers;

import it.uniroma3.siw.siwfood.entities.ricetta;
import it.uniroma3.siw.siwfood.repository.utenteRepository;
import it.uniroma3.siw.siwfood.services.utenteService;
import it.uniroma3.siw.siwfood.services.JWTService;
import it.uniroma3.siw.siwfood.responses.utenteResponse;
import it.uniroma3.siw.siwfood.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/utenti")
@RestController
public class userController {

    final utenteService utenteService;
    final utenteRepository utenteRepository;
    final JWTService jwtService;
    private final it.uniroma3.siw.siwfood.services.ricettaService ricettaService;

    public userController(utenteService utenteService, utenteRepository utenteRepository, JWTService jwtService, it.uniroma3.siw.siwfood.services.ricettaService ricettaService) {
        this.utenteService = utenteService;
        this.utenteRepository = utenteRepository;
        this.jwtService = jwtService;
        this.ricettaService = ricettaService;
    }

    @GetMapping
    public ResponseEntity<List<utenteResponse>> getListaUtenti() {
        List<User> users = utenteService.getAllUsers();
        List<utenteResponse> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(new utenteResponse(user.getUsername(), user.getNome(), user.getCognome(), user.getDob(), user.getId()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("{id}")
    public ResponseEntity<utenteResponse> getUserById(@PathVariable Long id) {
        User user = (User) utenteService.loadById(id);
        utenteResponse response = new utenteResponse(user.getUsername(), user.getNome(), user.getCognome(), user.getDob(), user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        User user = (User) utenteService.loadById(id);
        List<ricetta> ricette = new ArrayList<>(ricettaService.getTutteRicette());
        for (it.uniroma3.siw.siwfood.entities.ricetta ricetta : ricette) {
            if (ricetta.getAuthor() == user) {
                ricettaService.eliminaRicettaById(ricetta.getId());
            }
        }
        this.utenteService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<utenteResponse> authenticatedUser(@RequestHeader("Authorization") String authorizationHeader) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String token = authorizationHeader.substring(7);
        System.out.println("Token: " + token);
        if (principal instanceof User currentUser && this.jwtService.isTokenValid(token, currentUser)) {
            System.out.println("Token valido.");
            utenteResponse utenteResponse = new utenteResponse(currentUser.getUsername(), currentUser.getNome(), currentUser.getCognome(), currentUser.getDob(), currentUser.getId());
            return ResponseEntity.ok(utenteResponse);
        }
        else {
            System.out.println("Richiesta non valida.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/me")
    public ResponseEntity<utenteResponse> modifyUser(@RequestBody User modifiedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = utenteRepository.findByEmail(username);

        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (modifiedUser.getNome() != null) {
            currentUser.setNome(modifiedUser.getNome());
        }

        if (modifiedUser.getCognome() != null) {
            currentUser.setCognome(modifiedUser.getCognome());
        }

        if (modifiedUser.getEmail() != null) {
            currentUser.setEmail(modifiedUser.getEmail());
        }

        if (modifiedUser.getDob() != null) {
            currentUser.setDob(modifiedUser.getDob());
        }

        utenteRepository.save(currentUser);

        utenteResponse utenteResponse = new utenteResponse(currentUser.getUsername(), currentUser.getNome(), currentUser.getCognome(), currentUser.getDob(), currentUser.getId());
        return ResponseEntity.ok(utenteResponse);
    }
}
