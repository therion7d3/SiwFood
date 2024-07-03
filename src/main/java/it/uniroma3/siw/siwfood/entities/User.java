package it.uniroma3.siw.siwfood.entities;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@EnableAutoConfiguration
@Entity
@Table(name = "indirizzi")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nome")
    String nome;

    @Column(name= "cognome")
    String cognome;

    @Column(name = "datadinascita")
    String dob;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "password")
    String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private foto foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public foto getImage() {
        return foto;
    }

    public void setImage(foto foto) {
        this.foto = foto;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(ruolo ruolo : ruoloList) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ruolo.getName());
            authorityList.add(authority);
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ruolo> getRoleList() {
        return ruoloList;
    }

    public void setRoleList(List<ruolo> ruoloList) {
        this.ruoloList = ruoloList;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    List<ruolo> ruoloList;

    public void addRole(ruolo ruolo) {
        this.ruoloList.add(ruolo);
    }

    public void setRoleList(List<ruolo> ruoloList, List<GrantedAuthority> authorities) {
        this.ruoloList = ruoloList;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setImage(String image) {
        this.foto = new foto();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }
}