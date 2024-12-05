package fr.miage.orleans.m2.aar.authentification.repositories;

import fr.miage.orleans.m2.aar.authentification.modele.Utilisateur;
import org.springframework.data.repository.CrudRepository;

public interface RepoUtilisateur extends CrudRepository<Utilisateur,Long> {

    public Utilisateur findByEmail(String email);

    public Utilisateur findByEmailAndPassword(String email, String password);

}
