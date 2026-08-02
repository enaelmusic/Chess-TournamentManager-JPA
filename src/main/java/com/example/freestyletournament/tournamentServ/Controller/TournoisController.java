package com.example.freestyletournament.tournamentServ.Controller;

import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournois;
import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.PlayerTournois.PlayerTournois;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwiss;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.DernierMancheException;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.PlayerTournois.PlayerTournoisDAO;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.TreeSet;

@RestController
public class TournoisController {
    @Autowired
    private TournoisDAO tournoisDAO;
    @Autowired
    private PlayerSwissDAO playerSwissDAO;
    @Autowired
    private PlayerTournoisDAO playerTournoisDAO;
    @Autowired
    private ClassementTournoisDAO classementTournoisDAO;
    @Autowired
    private MatchSwissDAO matchSwissDAO;
    @Autowired
    private MancheSwissDAO mancheSwissDAO;

    @GetMapping("/getRound/{num_tournois}")
    public ArrayList<MancheSwiss> getManchesTournois(@PathVariable String num_tournois){
        return mancheSwissDAO.getManchesByTournoisNum(num_tournois);
    }

    @PostMapping("/sauverPlayerSwiss")
    public PlayerSwiss sauverPlayer(@RequestBody PlayerSwiss player){
        return playerSwissDAO.sauverPlayer(player);
    }
    // Staging //
    /**
     * Si Insert :
     *     Creer un tournois vide, attribuer un identifiant unique, renvoyer le tournois.
     * Si update
     *     Update tournois recu
     */
    //
    @PostMapping("/sauverTournois")
    public Tournois postTournois(@RequestBody Tournois tournois){
        return tournoisDAO.sauverTournois(tournois);
    }

    /**
     *     enregistrer Joueur dans la DB
     */
    @PostMapping("/sauverJoueurTournois")
    public void postJoueurTournois(@RequestBody PlayerTournois playerTournois){
        playerTournoisDAO.savePlayerTournois(playerTournois.getIdTournois(),playerTournois.getIdPlayer());
    }
    /**
     *     mettre a joueur la liste des participant du tournois.
     */
    @GetMapping("/playerTournoisList/{id}")
    public TreeSet<PlayerSwiss> getPlayerTournois(@PathVariable int id){
        return playerTournoisDAO.getPlayerByIdTournois(id);
    }

    /**
     *     le tournois est a jouer et completer,
     *     le tournois demare en renvoyant une liste de match correspondant au PREMIER ROUND(MancheSwiss)
     */
    @PostMapping("/starttournois")
    public TreeSet<MatchSwiss> startTournois(@RequestBody Tournois tournois){
        return tournoisDAO.startTournois(tournois);
    }

    /**
     * NextRound *
     * @param matches recoit les match de la round precedente pour enregister les scores
     * @return liste de match correspondant a la manche
     */
    @PostMapping("/nextround")
    public ResponseEntity<TreeSet<MatchSwiss>> nextManche(@RequestBody TreeSet<MatchSwiss> matches){
        try {
            return ResponseEntity.accepted().body(tournoisDAO.nextManche(matches));
        } catch (DernierMancheException e) {
            @Nullable TreeSet<MatchSwiss> errorMatch=null;
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(errorMatch);
        }
    }
    @GetMapping("/getClassementTournois/{id}")
    public ArrayList<ClassementTournois> getClassementTournois(@PathVariable int id){
        return classementTournoisDAO.getClassmenetTournois(id);
    }
    /**
     * getterSimple
     */
    @GetMapping("/getNamePlayer/{num_joueur}")
    public String getNamePlayer(String num_joueur){
        return playerSwissDAO.getPlayer(num_joueur).getNom();
    }

    @GetMapping("/getNameTournois/{num_tournois}")
    public String getNameTournois(String num_tournois){
        return tournoisDAO.getTournoisName(num_tournois);
    }

}
