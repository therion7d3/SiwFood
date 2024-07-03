package it.uniroma3.siw.siwfood.responses;

import it.uniroma3.siw.siwfood.entities.ingrediente;
import it.uniroma3.siw.siwfood.entities.ricetta;
import java.util.List;

public class ricettaResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final Long authorId;
    private final String authorName;
    private final List<ingrediente> listaIngredienti;
    private final byte[] immagine1;
    private final byte[] immagine2;
    private final byte[] immagine3;

    public ricettaResponse(ricetta ricetta) {
        this.id = ricetta.getId();
        this.title = ricetta.getTitle();
        this.description = ricetta.getDescription();
        this.authorId = ricetta.getAuthor().getId();
        this.authorName = ricetta.getAuthor().getNome();
        this.listaIngredienti = ricetta.getListaIngredienti();
        if (!ricetta.getImages().isEmpty()) {
            this.immagine1 = (ricetta.getImages().get(0) != null) ? ricetta.getImages().get(0).getData() : new byte[0];
        }
        else immagine1 = null;
        if (ricetta.getImages().size() > 1) {
            this.immagine2 = (ricetta.getImages().get(1) != null) ? ricetta.getImages().get(1).getData() : new byte[0];
        }
        else this.immagine2 = null;
        if (ricetta.getImages().size() > 2) {
            this.immagine3 = (ricetta.getImages().get(2) != null) ? ricetta.getImages().get(2).getData() : new byte[0];
        }
        else this.immagine3 = null;
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthorName() {
        return authorName;
    }

    public List<ingrediente> getListaIngredienti() {
        return listaIngredienti;
    }

    public byte[] getImmagine1() {
        return immagine1;
    }

    public byte[] getImmagine2() {
        return immagine2;
    }

    public byte[] getImmagine3() {
        return immagine3;
    }

    public Long getId() {
        return id;
    }
}

