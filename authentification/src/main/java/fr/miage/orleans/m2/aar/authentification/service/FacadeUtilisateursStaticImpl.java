package fr.miage.orleans.m2.aar.authentification.service;
import fr.miage.orleans.m2.aar.authentification.modele.Utilisateur;
import fr.miage.orleans.m2.aar.authentification.modele.exceptions.LoginDejaUtiliseException;
import fr.miage.orleans.m2.aar.authentification.modele.exceptions.UtilisateurInexistantException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class FacadeUtilisateursStaticImpl implements FacadeUtilisateurs {



    private final Map<String, Utilisateur> utilisateursMap;

    public FacadeUtilisateursStaticImpl() {
        utilisateursMap = new HashMap<>();
    }

    @Override
    public long getUtilisateurIntId(String email) throws UtilisateurInexistantException {
        return getUtilisateurByEmail(email).getIdUtilisateur();
    }

    @Override
    public Utilisateur getUtilisateurByEmail(String email) throws UtilisateurInexistantException {
        if (utilisateursMap.containsKey(email))
            return this.utilisateursMap.get(email);
        else
            throw new UtilisateurInexistantException();
    }

    @Override
    public Utilisateur inscrireUtilisateur(String email, String password) throws LoginDejaUtiliseException {
        if (utilisateursMap.containsKey(email))
            throw new LoginDejaUtiliseException();
        else {
            Utilisateur utilisateur = new Utilisateur(email, password);
            utilisateursMap.put(utilisateur.getEmail(), utilisateur);
            return utilisateur;
        }
    }

    @Override
    public boolean verifierMotDePasse(String email, String password) {
        if (utilisateursMap.containsKey(email)) {
            return utilisateursMap.get(email).verifierPassword(password);
        } else {
            return false;
        }
    }

    @Override
    public Collection<Utilisateur> getAllUtilisateurs() {
        return utilisateursMap.values();
    }

}
