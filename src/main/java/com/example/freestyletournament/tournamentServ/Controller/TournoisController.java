package com.example.freestyletournament.tournamentServ.Controller;

import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.PlayerTournois.PlayerTournois;
import com.example.freestyletournament.tournamentServ.Model.RoundSwiss.RoundSwiss;
import com.example.freestyletournament.tournamentServ.Model.RoundSwiss.RoundSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.PlayerTournois.PlayerTournoisDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.TreeSet;

@RestController
public class TournoisController {
    @Autowired
    private TournoisDAO tDao;
    @Autowired
    private PlayerSwissDAO pDAO;
    @Autowired
    private PlayerTournoisDAO ptDAO;
    @Autowired
    private RoundSwissDAO roundSwissDAO;

    @GetMapping("/getTournoisName")
    public String getTournoisName(Tournois tournois){
        return tDao.getTournoisName(tournois);
    }
    @GetMapping("/playerTournoisList/{id}")
    public TreeSet<PlayerSwiss> getPlayerTournois(@PathVariable int id){
        return ptDAO.getPlayerByIdTournois(id);
    }
    @GetMapping("/demarerTournois/{id}")
    public TreeSet<RoundSwiss> getRound(@PathVariable int id){
        TreeSet<PlayerSwiss> players = ptDAO.getPlayerByIdTournois(id);
        return roundSwissDAO.appariementAleatoir(id, players);
    }

    @PostMapping("/sauverTournois")
    public Tournois postTournois(@RequestBody Tournois tournois){
        return tDao.sauverTournois(tournois); // le controller renvoie automatiquement un entite
    }
    @PostMapping("/sauverPlayerSwiss")
    public PlayerSwiss sauverPlayer(@RequestBody PlayerSwiss player){
        return pDAO.sauverPlayer(player);
    }
    @PostMapping("/sauverJoueurTournois")
    public void postJoueurTournois(@RequestBody PlayerTournois playerTournois){
        ptDAO.savePlayerTournois(playerTournois.getIdTournois(),playerTournois.getIdPlayer());
    }
}
