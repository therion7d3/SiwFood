package it.uniroma3.siw.siwfood.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.uniroma3.siw.siwfood.entities.ingrediente;
import it.uniroma3.siw.siwfood.entities.ricetta;
import it.uniroma3.siw.siwfood.entities.User;
import it.uniroma3.siw.siwfood.responses.ricettaResponse;
import it.uniroma3.siw.siwfood.services.utenteService;
import it.uniroma3.siw.siwfood.services.fotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ricette")
public class ricettaController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final it.uniroma3.siw.siwfood.services.ricettaService ricettaService;
    private final utenteService utenteService;
    private final fotoService fotoService;

    public ricettaController(it.uniroma3.siw.siwfood.services.ricettaService ricettaService, utenteService utenteService, fotoService fotoService) {
        this.ricettaService = ricettaService;
        this.utenteService = utenteService;
        this.fotoService = fotoService;
    }

    @PostMapping("/addricetta")
    public ResponseEntity<?> createRicetta(@RequestBody JsonNode requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        String token = (String) authentication.getCredentials();

        try {
            ricetta savedRicetta = new ricetta();
            String title = requestBody.get("title").asText();
            String description = requestBody.get("description").asText();
            Long userId = requestBody.get("authorId").asLong();


            User autore = (User) utenteService.loadById(userId);

            if (autore == null) {
                return ResponseEntity.notFound().build();
            }

            // Gestione della lista degli ingredienti
            List<ingrediente> listaIngredienti = new ArrayList<>();
            JsonNode ingredientiNode = requestBody.get("listaIngredienti");
            if (ingredientiNode != null && ingredientiNode.isArray()) {
                for (final JsonNode ingredientNode : ingredientiNode) {
                    ingrediente ingrediente = objectMapper.treeToValue(ingredientNode, it.uniroma3.siw.siwfood.entities.ingrediente.class);
                    ingrediente.setRicetta(savedRicetta);
                    listaIngredienti.add(ingrediente);
                }
            } else {
                // Se listaIngredienti non è presente o non è un array, la lista degli ingredienti sarà vuota
                System.out.println("Nessun ingrediente fornito nel JSON.");
                // Puoi scegliere di aggiungere un messaggio di log o altre azioni appropriate
            }


            /*
            Inizializzazione effettiva dei parametri della ricetta
             */

            savedRicetta.setTitle(title);
            savedRicetta.setListaIngredienti(listaIngredienti);
            savedRicetta.setDescription(description);
            savedRicetta.setAuthor(autore);
            ricettaService.salvaRicetta(savedRicetta);

            ricettaResponse response = new ricettaResponse(savedRicetta);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Log dell'errore
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ricettaResponse>> getAllRicette() {
        List<ricetta> ricette = ricettaService.getTutteRicette();
        List<ricettaResponse> ricetteResponse = ricette.stream()
                .map(ricettaResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ricetteResponse);
    }

    @GetMapping("/madeby/{id}")
    public ResponseEntity<List<ricettaResponse>> getRicetteMadeByUser(@PathVariable Long id) {
        User user = (User) utenteService.loadById(id);
        if (user != null) {
            List<ricetta> ricette = ricettaService.getRicetteByChef(user);
            List<ricettaResponse> ricetteResponse = ricette.stream()
                    .map(ricettaResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ricetteResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ricettaResponse> getRicettaById(@PathVariable Long id) {
        ricetta ricetta = ricettaService.getRicettaById(id);
        System.out.println(ricetta.getTitle());
        ricettaResponse response = new ricettaResponse(ricetta);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRicettaById(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        User user = (User) authentication.getPrincipal();

        boolean isDeleted = ricettaService.eliminaRicettaChef(id, user);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non sei autorizzato a eliminare questa ricetta.");
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<?> modifyRicettaById(@PathVariable Long id, @RequestBody JsonNode requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        ricetta savedRicetta = this.ricettaService.getRicettaById(id);

        if (!(principal instanceof User currentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato.");
        }

        User author = savedRicetta.getAuthor();

        if (!author.equals(currentUser) && !(currentUser.getRoleList().get(0).getId() == 2)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato a modificare questa ricetta.");
        }

        try {
            String title = requestBody.get("title").asText();
            String description = requestBody.get("description").asText();
            Long userId = requestBody.get("authorId").asLong();


            User autore = (User) utenteService.loadById(userId);

            if (autore == null) {
                return ResponseEntity.notFound().build();
            }

            // Gestione della lista degli ingredienti

            List<ingrediente> listaIngredienti = new ArrayList<>();
            JsonNode ingredientiNode = requestBody.get("listaIngredienti");
            if (ingredientiNode != null && ingredientiNode.isArray()) {
                for (final JsonNode ingredientNode : ingredientiNode) {
                    ingrediente ingrediente = objectMapper.treeToValue(ingredientNode, it.uniroma3.siw.siwfood.entities.ingrediente.class);
                    ingrediente.setRicetta(savedRicetta);
                    listaIngredienti.add(ingrediente);
                }
            } else {
                // Se listaIngredienti non è presente o non è un array, la lista degli ingredienti sarà vuota
                System.out.println("Nessun ingrediente fornito nel JSON.");
                // Puoi scegliere di aggiungere un messaggio di log o altre azioni appropriate
            }


            /*
            Inizializzazione effettiva dei parametri della ricetta
             */

            savedRicetta.setTitle(title);

            // Rimuovi ingredienti non più presenti
            savedRicetta.getListaIngredienti().removeIf(ingrediente -> !listaIngredienti.contains(ingrediente));

            // Aggiungi o aggiorna ingredienti esistenti
            for (ingrediente nuovoIngrediente : listaIngredienti) {
                if (!savedRicetta.getListaIngredienti().contains(nuovoIngrediente)) {
                    nuovoIngrediente.setRicetta(savedRicetta);
                    savedRicetta.getListaIngredienti().add(nuovoIngrediente);
                }
            }

            savedRicetta.setDescription(description);
            ricettaService.salvaRicetta(savedRicetta);

            ricettaResponse response = new ricettaResponse(savedRicetta);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Log dell'errore
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint per ottenere una ricetta per titolo
    @GetMapping("/byTitle")
    public ResponseEntity<ricetta> getRicettaByTitle(@RequestParam String title) {
        ricetta ricetta = ricettaService.getRicettaByNome(title);
        if (ricetta != null) {
            return ResponseEntity.ok(ricetta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
