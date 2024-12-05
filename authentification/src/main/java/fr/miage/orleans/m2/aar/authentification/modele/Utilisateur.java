package fr.miage.orleans.m2.aar.authentification.modele;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity

public class Utilisateur {
    @Id
    @GeneratedValue
    private long idUtilisateur;

    private String email;
    private String password;

    @ElementCollection
    List<Role> roles;

    public Utilisateur(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Utilisateur() {

    }


    public long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean verifierPassword(String motDePasse) {
        return this.password.equals(motDePasse);
    }
}
