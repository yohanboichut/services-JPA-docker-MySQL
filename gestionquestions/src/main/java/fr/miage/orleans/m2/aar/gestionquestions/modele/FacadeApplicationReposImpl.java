package fr.miage.orleans.m2.aar.gestionquestions.modele;

import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.AccessIllegalAUneQuestionException;
import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.QuestionInexistanteException;
import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.UtilisateurInexistantException;
import fr.miage.orleans.m2.aar.gestionquestions.modele.repositories.RepoQuestion;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component("facadeApplicationRepos")
public class FacadeApplicationReposImpl implements FacadeApplication {

    RepoQuestion repoQuestion;


    public FacadeApplicationReposImpl(RepoQuestion repoQuestion) {
        this.repoQuestion = repoQuestion;
    }

    @Override
    public Question ajouterUneQuestion(int idUtilisateur, String libelleQuestion) {
        Question question = new Question(idUtilisateur, libelleQuestion);
        repoQuestion.save(question);
        return question;
    }

    @Override
    public void repondreAUneQuestion(String idQuestion, String reponse) throws QuestionInexistanteException {
        Question question = repoQuestion.findById(idQuestion).orElseThrow(() -> new QuestionInexistanteException());
        question.setReponse(reponse);
        repoQuestion.save(question);

    }

    @Override
    public Collection<Question> getQuestionsSansReponses() {
        return repoQuestion.findByReponseIsNull();
    }

    @Override
    public Collection<Question> getQuestionsAvecReponsesByUser(int idUtilisateur) throws UtilisateurInexistantException {
        return repoQuestion.findByIdUtilisateurAndReponseIsNotNull(idUtilisateur);
    }

    @Override
    public Collection<Question> getQuestionsSansReponsesByUser(int idUtilisateur) throws UtilisateurInexistantException {
        return repoQuestion.findByWithoutReponse(idUtilisateur);
    }

    @Override
    public Collection<Question> getToutesLesQuestionsByUser(int idUtilisateur) throws UtilisateurInexistantException {
        return repoQuestion.findByIdUtilisateur(idUtilisateur);
    }

    @Override
    public Collection<Question> getToutesLesQuestions() {
        return (Collection<Question>) repoQuestion.findAll();
    }

    @Override
    public Question getQuestionByIdPourUnUtilisateur(int idUtilisateur, String idQuestion) throws QuestionInexistanteException, AccessIllegalAUneQuestionException, UtilisateurInexistantException {
        return repoQuestion.findByIdUtilisateurAndIdQuestion(idUtilisateur, idQuestion).orElseThrow(() -> new QuestionInexistanteException());    }
}
