package fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccessIllegalAUneQuestionException extends Exception {
}
