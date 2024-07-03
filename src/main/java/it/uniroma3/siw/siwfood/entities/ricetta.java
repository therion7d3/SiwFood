package it.uniroma3.siw.siwfood.entities;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.*;

@EnableAutoConfiguration
@Entity
@Table(name = "ricette")
public class ricetta {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identificatore

    private String title; // titolo della ricetta

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_autore")
    private User author;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ingrediente> listaIngredienti = new ArrayList<>();

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL)
    @OrderColumn
    private List<foto> fotos;

    public ricetta() {
        this.listaIngredienti = new ArrayList<>();
        this.fotos = new ArrayList<>();
        foto foto1 = new foto();
        foto foto2 = new foto();
        foto foto3 = new foto();
        foto1.setRicetta(this);
        foto2.setRicetta(this);
        foto3.setRicetta(this);
        this.fotos.add(foto1);
        this.fotos.add(foto2);
        this.fotos.add(foto3);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ingrediente> getListaIngredienti() {
        return listaIngredienti;
    }

    public void setListaIngredienti(List<ingrediente> listaIngredienti) {
        this.listaIngredienti = listaIngredienti;
    }

    public void addIngrediente(ingrediente ingrediente) {
        this.listaIngredienti.add(ingrediente);
    }

    public List<foto> getImages() {
        return fotos;
    }

    public void addImage(foto newFoto) {
        this.fotos.add(newFoto);
    }


    public void replaceImage(int index, foto foto) {
        this.fotos.set(index, foto);
    }

    public void removeImage(foto foto) {
        this.fotos.remove(foto);
        foto.setRicetta(null);
    }

    public void setImages(List<foto> fotos) {
        this.fotos = fotos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
