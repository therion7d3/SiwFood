package it.uniroma3.siw.siwfood.entities;

import jakarta.persistence.*;

@Entity
public class foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bytea")
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ricetta_id")
    private it.uniroma3.siw.siwfood.entities.ricetta ricetta;

    @Column(name = "images_order")
    private int imageOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Lob
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public it.uniroma3.siw.siwfood.entities.ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(it.uniroma3.siw.siwfood.entities.ricetta ricetta) {
        this.ricetta = ricetta;
    }

    public int getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }

}


