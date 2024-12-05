package fr.miage.orleans.m2.aar.gestionquestions.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Question {

    private int idUtilisateur;
    private String libelleQuestion;
    private String reponse;
    @Id
    private String idQuestion;


    public Question() {
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setLibelleQuestion(String libelleQuestion) {
        this.libelleQuestion = libelleQuestion;
    }

    public Question(int idUtilisateur, String question) {
        this.idUtilisateur = idUtilisateur;
        this.libelleQuestion = question;
        this.idQuestion = UUID.randomUUID().toString();
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getLibelleQuestion() {
        return libelleQuestion;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}
