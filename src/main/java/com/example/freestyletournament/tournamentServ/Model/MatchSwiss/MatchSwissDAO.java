package com.example.freestyletournament.tournamentServ.Model.MatchSwiss;

import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournoisRep;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwissRep;
import com.example.freestyletournament.tournamentServ.Model.ModelService.MancheEnum;
import com.example.freestyletournament.tournamentServ.Model.ModelService.MatchStatus;
import com.example.freestyletournament.tournamentServ.Model.ModelService.ParticipantEnum;
import com.example.freestyletournament.tournamentServ.Model.ModelService.UniqueService;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissDTO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisModel;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchSwissDAO {
    @Autowired
    ServiceAppariement serviceAppariement;
    @Autowired
    MatchSwissRepestory matchSwissRepestory;
    @Autowired
    ClassementTournoisRep classementTournoisRep;
    @Autowired
    ClassementTournoisDAO classementTournoisDAO;
    @Autowired
    TournoisModel tournoisM;
    @Autowired
    private MancheSwissRep mancheSwissRep;
    private UniqueService us = new UniqueService();
    private static final Logger log = LoggerFactory.getLogger(TournoisDAO.class);
    private final Random rand = new Random();

    public TreeSet<MatchSwiss> appariementAleatoir(String num_manche, TreeSet<PlayerSwissDTO> playerSwisses) {
        return serviceAppariement.appariementAleatoir(num_manche,playerSwisses);
    }

    public MatchSwiss changerStatus(MatchSwiss rs) {
        return matchSwissRepestory.save(rs);
    }
    public TreeSet<MatchSwiss> appariementPlayer(String num_manche, int idTournois, int mancheInt) throws AppariementException{
        return  serviceAppariement.appariementPlayer(num_manche,idTournois,mancheInt);
    }
    public TreeSet<MatchSwiss> getMatchByManche(String num_manche){
        TreeSet<MatchSwiss> m = matchSwissRepestory.findMatchBynumManche(num_manche);
        return m;
    }
    public void annulerMatch(String num_manche, int statusManche){
        TreeSet<MatchSwiss> matchManche = matchSwissRepestory.findMatchBynumManche(num_manche);
        if(statusManche==MancheEnum.ANNULE.getTyni()){
            for(MatchSwiss m : matchManche){
                // m.setStatus(MatchStatus.ANNULE.getTyni());
                // OU
                matchSwissRepestory.delete(m);
            }
        }
        else{
            log.info("la manche n'est pas annulé");
        }
    }
    public String dernierMancheEnCours(int idTournois){
        return matchSwissRepestory.getLastMancheEnCours(idTournois);
    }
//    public TreeSet<MatchSwissDTO> chargerHistorique(int i) {
//        return matchSwissRepestory.chargerHistorique(i);
//    }
}