package fr.miage.orleans.m2.aar.authentification.service;

import fr.miage.orleans.m2.aar.authentification.modele.Utilisateur;
import fr.miage.orleans.m2.aar.authentification.modele.exceptions.LoginDejaUtiliseException;
import fr.miage.orleans.m2.aar.authentification.modele.exceptions.UtilisateurInexistantException;

import java.util.Collection;

public interface FacadeUtilisateurs {
    long getUtilisateurIntId(String email) throws UtilisateurInexistantException;

    Utilisateur getUtilisateurByEmail(String email) throws UtilisateurInexistantException;

    Utilisateur inscrireUtilisateur(String email, String password) throws LoginDejaUtiliseException;

    boolean verifierMotDePasse(String email, String password);

    Collection<Utilisateur> getAllUtilisateurs();
}
