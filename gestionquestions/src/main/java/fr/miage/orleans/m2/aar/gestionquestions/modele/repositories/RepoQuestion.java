package fr.miage.orleans.m2.aar.gestionquestions.modele.repositories;

import fr.miage.orleans.m2.aar.gestionquestions.modele.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

public interface RepoQuestion extends CrudRepository<Question, String> {
    public Collection<Question> findByReponseIsNull();

    public Collection<Question> findByIdUtilisateurAndReponseIsNotNull(int idUtilisateur);




    @Query("Select q From Question q " +
            "where q.idUtilisateur=:idUtilisateur " +
            "and q.reponse is null")
    public Collection<Question> findByWithoutReponse(@Param("idUtilisateur") int idUtilisateur);

    public Collection<Question> findByIdUtilisateur(int idUtilisateur);

    public Optional<Question> findByIdUtilisateurAndIdQuestion(int idUtilisateur, String idQuestion);




}
