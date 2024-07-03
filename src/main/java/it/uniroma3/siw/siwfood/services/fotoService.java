package it.uniroma3.siw.siwfood.services;

import it.uniroma3.siw.siwfood.entities.foto;
import it.uniroma3.siw.siwfood.entities.ricetta;
import it.uniroma3.siw.siwfood.repository.ricettaRepository;
import it.uniroma3.siw.siwfood.repository.utenteRepository;
import it.uniroma3.siw.siwfood.entities.User;
import it.uniroma3.siw.siwfood.repository.fotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class fotoService {

    private final utenteRepository userRepo;
    private final fotoRepository imageRepo;
    private final ricettaRepository ricettaRepo;

    public fotoService(utenteRepository userRepo, fotoRepository imageRepo, ricettaRepository ricettaRepo) {
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
        this.ricettaRepo = ricettaRepo;
    }

    public Optional<foto> getImageById(Long id) {
        return imageRepo.findById(id);
    }

    public Optional<foto> getImageByUserId(Long userId) {
        return imageRepo.findByUserId(userId);
    }

    public foto saveImage(foto foto) {
        return imageRepo.save(foto);
    }

    public foto saveImageForUser(Long userId, MultipartFile file) throws IOException {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            foto foto = new foto();
            foto.setData(file.getBytes());
            foto.setUser(user);

            return imageRepo.save(foto);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public foto saveImageForRicetta(Long ricettaId, MultipartFile file, int index) throws IOException {
        ricetta ricetta = ricettaRepo.findById(ricettaId).orElseThrow(() -> new RuntimeException("Ricetta not found"));

        foto foto;
        if (ricetta.getImages().size() > index && ricetta.getImages().get(index) != null) {
            foto = ricetta.getImages().get(index);
            System.out.println("Replacing image " + foto.getId());
            foto.setData(file.getBytes());
            foto.setRicetta(ricetta);
        } else {
            System.out.println("Adding new image");
            foto = new foto();
            foto.setData(file.getBytes());
            foto.setRicetta(ricetta);
            if (ricetta.getImages().size() > index) {
                ricetta.getImages().set(index, foto);
            } else {
                ricetta.getImages().add(foto);
            }
            imageRepo.save(foto);
        }

        List<foto> fotos = ricetta.getImages();
        for (int i = 0; i < fotos.size(); i++) {
            if (fotos.get(i) != null) {
                fotos.get(i).setImageOrder(i);
            }
        }

        ricettaRepo.save(ricetta);
        return foto;
    }

    public void deleteImage(Long id) {
        imageRepo.deleteById(id);
    }
}
