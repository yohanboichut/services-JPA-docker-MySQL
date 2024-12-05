package fr.miage.orleans.m2.aar.authentification.service;
import fr.miage.orleans.m2.aar.authentification.modele.Utilisateur;
import fr.miage.orleans.m2.aar.authentification.modele.exceptions.LoginDejaUtiliseException;
import fr.miage.orleans.m2.aar.authentification.modele.exceptions.UtilisateurInexistantException;
import fr.miage.orleans.m2.aar.authentification.repositories.RepoUtilisateur;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("repo")
public class FacadeUtilisateursRepoImpl implements FacadeUtilisateurs {


    private RepoUtilisateur repoUtilisateur;

    public FacadeUtilisateursRepoImpl(RepoUtilisateur repoUtilisateur) {

        this.repoUtilisateur = repoUtilisateur;
    }

    @Override
    public long getUtilisateurIntId(String email) throws UtilisateurInexistantException {
        return repoUtilisateur.findByEmail(email).getIdUtilisateur();
    }

    @Override
    public Utilisateur getUtilisateurByEmail(String email) throws UtilisateurInexistantException {
        Utilisateur u=repoUtilisateur.findByEmail(email);

        if (u!=null)
            return u;
        else
            throw new UtilisateurInexistantException();
    }

    @Override
    public Utilisateur inscrireUtilisateur(String email, String password) throws LoginDejaUtiliseException {
        Utilisateur u=repoUtilisateur.findByEmail(email);
        if (u!=null)
            throw new LoginDejaUtiliseException();
        else {
            Utilisateur utilisateur = new Utilisateur(email, password);
            repoUtilisateur.save(utilisateur);
            return utilisateur;
        }
    }

    @Override
    public boolean verifierMotDePasse(String email, String password) {
        Utilisateur u=repoUtilisateur.findByEmailAndPassword(email,password);
        return (u!=null);
    }

    @Override
    public Collection<Utilisateur> getAllUtilisateurs() {
        Collection<Utilisateur> cu=new ArrayList();
        for (Utilisateur u : repoUtilisateur.findAll() ) {
            cu.add(u);
        }
        return cu;
    }

}
