package fr.miage.orleans.m2.aar.authentification.controleur;

import fr.miage.orleans.m2.aar.authentification.controleur.dtos.LoginDTO;
import fr.miage.orleans.m2.aar.authentification.service.FacadeUtilisateurs;
import fr.miage.orleans.m2.aar.authentification.modele.Utilisateur;
import fr.miage.orleans.m2.aar.authentification.modele.exceptions.LoginDejaUtiliseException;
import fr.miage.orleans.m2.aar.authentification.modele.exceptions.UtilisateurInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;

@RestController
@RequestMapping("/api")
public class Controleur {

    @Qualifier("repo")
    @Autowired
    private FacadeUtilisateurs facadeUtilisateurs;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private Function<Utilisateur,String> genereToken;

    @PostMapping("/utilisateurs")
    public ResponseEntity<Utilisateur> inscrire(@RequestParam String email,
                                                @RequestParam String password,
                                                UriComponentsBuilder base)
    {
        Utilisateur utilisateur;
        try {
            utilisateur = facadeUtilisateurs.inscrireUtilisateur(email, passwordEncoder.encode(password));
        } catch (LoginDejaUtiliseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        URI location = base.path("/api/utilisateurs/{idUtilisateur}")
                .buildAndExpand(utilisateur.getIdUtilisateur())
                .toUri();
        return ResponseEntity.created(location).header("Authorization","Bearer "+genereToken.apply(utilisateur)).body(utilisateur);
    }




    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            Utilisateur utilisateur = facadeUtilisateurs.getUtilisateurByEmail(loginDTO.email());
            if (passwordEncoder.matches(loginDTO.password(), utilisateur.getPassword())) {
                return ResponseEntity.status(HttpStatus.CREATED).header("Authorization","Bearer "+genereToken.apply(utilisateur)).build();
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }





}