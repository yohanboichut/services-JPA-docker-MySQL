package fr.miage.orleans.m2.aar.authentification.modele.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UtilisateurInexistantException extends Exception {
}
