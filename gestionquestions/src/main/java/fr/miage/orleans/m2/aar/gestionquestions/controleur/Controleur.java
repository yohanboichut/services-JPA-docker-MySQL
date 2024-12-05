package fr.miage.orleans.m2.aar.gestionquestions.controleur;

import fr.miage.orleans.m2.aar.gestionquestions.modele.FacadeApplication;
import fr.miage.orleans.m2.aar.gestionquestions.modele.Question;
import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.AccessIllegalAUneQuestionException;
import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.QuestionInexistanteException;
import fr.miage.orleans.m2.aar.gestionquestions.modele.exceptions.UtilisateurInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/forum/api")
public class Controleur {


    @Qualifier("facadeApplicationRepos")
    @Autowired
    private FacadeApplication facadeApplication;







    @PostMapping("/utilisateurs/{idUtilisateur}/questions")
    public ResponseEntity<Question> ajouterQuestion(@PathVariable int idUtilisateur,
                                                    @RequestBody String libelleQuestion,
                                                    JwtAuthenticationToken jwt,
                                                    UriComponentsBuilder base)
            throws UtilisateurInexistantException
    {
        int id = Integer.valueOf((String)jwt.getTokenAttributes().get("idUtilisateur"));

        if (idUtilisateur == id) {
            Question question = facadeApplication.ajouterUneQuestion(idUtilisateur, libelleQuestion);
            URI location = base.path("/utilisateurs/{idUtilisateur}/questions/{idQuestion}")
                    .buildAndExpand(idUtilisateur, question.getIdQuestion())
                    .toUri();
            return ResponseEntity.created(location).body(question);
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PatchMapping("/questions/{idQuestion}")
    public ResponseEntity<String> repondreQuestion(@PathVariable String idQuestion,
                                                   @RequestBody String reponse)
            throws QuestionInexistanteException
    {
        this.facadeApplication.repondreAUneQuestion(idQuestion, reponse);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/utilisateurs/{idUtilisateur}/questions")
    public ResponseEntity<Collection<Question>> getSomeQuestionsByUtilisateur(@PathVariable int idUtilisateur,
                                                                              @RequestParam Optional<String> filtre,
                                                                              JwtAuthenticationToken jwt)
    {
        try {
            int id = Integer.valueOf((String)jwt.getTokenAttributes().get("id"));
            String f = filtre.orElse("sansFiltre");

            if (idUtilisateur == id) {
                return switch (f) {
                    case "sansReponse" ->
                            ResponseEntity.ok(facadeApplication.getQuestionsSansReponsesByUser(idUtilisateur));
                    case "avecReponse" ->
                            ResponseEntity.ok(facadeApplication.getQuestionsAvecReponsesByUser(idUtilisateur));
                    default -> ResponseEntity.ok(facadeApplication.getToutesLesQuestionsByUser(idUtilisateur));
                };
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/utilisateurs/{idUtilisateur}/questions/{idQuestion}")
    public ResponseEntity<Question> getQuestionByUtilisateur(@PathVariable int idUtilisateur,
                                                             @PathVariable String idQuestion,
                                                             JwtAuthenticationToken jwt)
            throws UtilisateurInexistantException, AccessIllegalAUneQuestionException, QuestionInexistanteException
    {
        int id = Integer.valueOf((String)jwt.getTokenAttributes().get("id"));
        if (idUtilisateur == id) {
            Question question = facadeApplication.getQuestionByIdPourUnUtilisateur(idUtilisateur, idQuestion);
            return ResponseEntity.ok(question);
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/questions")
    public ResponseEntity<Collection<Question>> getQuestion(@RequestParam Optional<String> filtre)
    {
        String f = filtre.orElse("sansFiltre");
        if ("sansReponse".equals(f)) {
            return ResponseEntity.ok(facadeApplication.getQuestionsSansReponses());
        } else {
            return ResponseEntity.ok(facadeApplication.getToutesLesQuestions());
        }
    }

}