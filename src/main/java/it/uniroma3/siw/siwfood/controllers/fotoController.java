    package it.uniroma3.siw.siwfood.controllers;

    import it.uniroma3.siw.siwfood.entities.foto;
    import it.uniroma3.siw.siwfood.responses.fotoResponse;
    import it.uniroma3.siw.siwfood.services.fotoService;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MaxUploadSizeExceededException;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.Optional;

    @RestController
    @RequestMapping("/foto")
    public class fotoController {

        private final fotoService fotoService;

        public fotoController(fotoService fotoService) {
            this.fotoService = fotoService;
        }

        @GetMapping("/{id}")
        public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
            Optional<foto> image = fotoService.getImageById(id);
            if (image.isPresent()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jpg\"")
                        .body(image.get().getData());
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public ResponseEntity<foto> createImage(@RequestParam("file") MultipartFile file) {
            try {
                foto foto = new foto();
                foto.setData(file.getBytes());
                foto savedFoto = fotoService.saveImage(foto);
                return ResponseEntity.ok(savedFoto);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @GetMapping("/utente/{userId}")
        public ResponseEntity<byte[]> getImageByUserId(@PathVariable Long userId) {
            Optional<foto> image = fotoService.getImageByUserId(userId);
            if (image.isPresent()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("image/jpeg"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"user_" + userId + ".jpg\"")
                        .body(image.get().getData());
            } else {
                return ResponseEntity.notFound().build();
            }
        }



        @PostMapping("/upload/utente/{userId}")
        public ResponseEntity<fotoResponse> uploadImageForUser(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
            try {
                Optional<foto> pfp = fotoService.getImageByUserId(userId);
                if (pfp.isPresent()) {
                    // Se esiste già un'immagine per l'utente, aggiorna i dati dell'immagine
                    foto profileFoto = pfp.get();
                    profileFoto.setData(file.getBytes());
                    fotoService.saveImage(profileFoto); // Salva l'immagine aggiornata

                    // Costruisci e restituisci la risposta con i dati aggiornati dell'immagine
                    fotoResponse response = new fotoResponse(profileFoto.getId(), profileFoto.getData(),
                            profileFoto.getUser().getId(),
                            profileFoto.getRicetta() != null ? profileFoto.getRicetta().getId() : null);
                    return ResponseEntity.ok(response);
                } else {
                    // Se non esiste un'immagine per l'utente, salva una nuova immagine
                    foto savedFoto = fotoService.saveImageForUser(userId, file);

                    // Costruisci e restituisci la risposta con i dati della nuova immagine salvata
                    fotoResponse response = new fotoResponse(savedFoto.getId(), savedFoto.getData(),
                            savedFoto.getUser().getId(),
                            savedFoto.getRicetta() != null ? savedFoto.getRicetta().getId() : null);
                    return ResponseEntity.ok(response);
                }
            } catch (MaxUploadSizeExceededException e) {
                return ResponseEntity.badRequest().build();
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @PostMapping("/upload/ricetta/{recipeId}")
        public ResponseEntity<fotoResponse> uploadImageForRecipe(@PathVariable Long recipeId, @RequestParam("file") MultipartFile file, int index) {
            try {
                foto savedFoto = fotoService.saveImageForRicetta(recipeId, file, index);
                fotoResponse response = new fotoResponse(savedFoto.getId(), savedFoto.getData(), savedFoto.getRicetta().getId(), savedFoto.getUser()  != null ? savedFoto.getUser().getId() : null);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            } catch (Exception e) {
                // Log dell'eccezione per identificare la causa del problema
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
            fotoService.deleteImage(id);
            return ResponseEntity.noContent().build();
        }
    }
