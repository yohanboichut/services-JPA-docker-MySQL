package fr.miage.orleans.m2.aar.gestionquestions.modele;

import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.AccessIllegalAUneQuestionException;
import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.QuestionInexistanteException;
import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.UtilisateurInexistantException;

import java.util.Collection;

public interface FacadeApplication {
    Question ajouterUneQuestion(int idUtilisateur, String libelleQuestion);

    void repondreAUneQuestion(String idQuestion, String reponse) throws QuestionInexistanteException;

    Collection<Question> getQuestionsSansReponses();

    Collection<Question> getQuestionsAvecReponsesByUser(int idUtilisateur) throws UtilisateurInexistantException;

    Collection<Question> getQuestionsSansReponsesByUser(int idUtilisateur) throws UtilisateurInexistantException;

    Collection<Question> getToutesLesQuestionsByUser(int idUtilisateur) throws UtilisateurInexistantException;

    Collection<Question> getToutesLesQuestions();

    Question getQuestionByIdPourUnUtilisateur(int idUtilisateur, String idQuestion) throws QuestionInexistanteException, AccessIllegalAUneQuestionException, UtilisateurInexistantException;
}
