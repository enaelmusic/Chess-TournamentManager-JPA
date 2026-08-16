package com.example.freestyletournament.tournamentServ.Controller;

import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementDTO;
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
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDTO;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.TreeSet;

@RestController
public class TournoisController {
    private static final Logger log = LoggerFactory.getLogger(TournoisController.class);
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
    public ResponseEntity<TreeSet<MatchSwiss>> startTournois(@RequestBody Tournois tournois){
        TreeSet<MatchSwiss> matchSwissTreeSet = tournoisDAO.startTournois(tournois);
        TournoisDTO t = new TournoisDTO(tournois.getNbr_manche(),tournois.getName(),tournois.getNum_tournois(),tournois.getStatusTournois());
        return ResponseEntity.accepted().
                header("numManche","1").
                header("mancheMax",t.getNbr_manches()+"").
                body(matchSwissTreeSet);
    }

    /**
     * NextRound *
     * @param matches recoit les match de la round precedente pour enregister les scores
     * @return liste de match correspondant a la manche
     */
    @PostMapping("/nextround")
    public ResponseEntity<TreeSet<MatchSwiss>> nextManche(@RequestBody TreeSet<MatchSwiss> matches){
        try {
            TreeSet<MatchSwiss> matcheSwisses = tournoisDAO.nextManche(matches);
            MancheSwiss m = mancheSwissDAO.getMancheByNum(matcheSwisses.getFirst().getNum_manche());
            log.info("NumManche pour DTO"+matcheSwisses.getFirst().getNum_manche());
            TournoisDTO t = tournoisDAO.getTournoisDto(matcheSwisses.getFirst().getNum_manche());
            return ResponseEntity.accepted().
                    header("numManche",m.getInt_manche()+"").
                    header("mancheMax",t.getNbr_manches()+"").
                    body(matcheSwisses);
        } catch (DernierMancheException e) {
            @Nullable TreeSet<MatchSwiss> errorMatch=null;
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(errorMatch);
        }
    }
    @GetMapping("/getClassementTournois/{id}")
    public ArrayList<ClassementDTO> getClassementTournois(@PathVariable int id){
        return classementTournoisDAO.getClassmenetTournois(id);
    }
    /**
     * getterSimple
     */
    @GetMapping("/getNamePlayer/{id}")
    public String getNamePlayer(@PathVariable int id){
        log.info("l'id recu dans le controller : {}", id);
        return playerSwissDAO.getPlayer(id).getNom();
    }

    @GetMapping("/getNameTournois/{num_tournois}")
    public String getNameTournois(String num_tournois){
        return tournoisDAO.getTournoisName(num_tournois);
    }

    @GetMapping("/getManchesTournois/{idTournois}")
    public ArrayList<MancheSwiss> getMancheTournois(@PathVariable int idTournois){
        return mancheSwissDAO.getManchesByTournoisId(idTournois);
    }

}
