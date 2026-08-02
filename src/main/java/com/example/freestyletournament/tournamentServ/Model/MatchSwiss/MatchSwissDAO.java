package com.example.freestyletournament.tournamentServ.Model.MatchSwiss;

import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournoisRep;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwissRep;
import com.example.freestyletournament.tournamentServ.Model.ModelService.UniqueService;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

@Service
public class MatchSwissDAO {
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

    public TreeSet<MatchSwiss> appariementAleatoir(String num_manche, TreeSet<PlayerSwiss> playerSwisses) {
        log.info("je rentre en fonction appariemnt aleatoir: " + num_manche);
        // MancheSwiss manche = mancheSwissRep.findIdMancheByManche(num_manche);
//        log.info("manche trouver id= " + manche.getId());
        TreeSet<MatchSwiss> resultSet = new TreeSet<MatchSwiss>();
        ArrayList<PlayerSwiss> players = new ArrayList<>();
        for (PlayerSwiss ps : playerSwisses) {
            players.add(ps);
        }
        int numTable = 1;
        while (players.size() > 0) {
            MatchSwiss match = new MatchSwiss();
            String num_match = num_manche + "->" + numTable;
            if (players.size() == 1) {
                resultSet.add(buildPlayerBye(num_manche, players, num_match, numTable));
                players.removeFirst();
            } else {
                int randint = rand.nextInt(players.size());
                log.info("randint = " + randint + "player size =" + players.size());
                PlayerSwiss pW = players.get(randint);
                players.remove(randint);
                randint = rand.nextInt(players.size());
                PlayerSwiss pB = players.get(randint);
                players.remove(randint);

                match.buildMatch(num_manche, pW.getId(), pW.getNom(), pB.getId(), pB.getNom(), num_match, numTable);
                numTable++;
                log.info("num manche : " + match.getNum_manche());
                match = matchSwissRepestory.save(match);
                resultSet.add(match);
            }
        }
        return resultSet;
    }

    public MatchSwiss buildPlayerBye(String num_manche, ArrayList<PlayerSwiss> players, String num_match, int numTable) {
        MatchSwiss match = new MatchSwiss();
        PlayerSwiss pW = players.getFirst();
        match.buildMatch(num_manche, pW.getId(), pW.getNom(), pW.getId(), "BYE", num_match, numTable);
        match.setStatus(4);
        match.setTableNum(0);
        matchSwissRepestory.save(match);
        return match;
    }

    public MatchSwiss changerStatus(MatchSwiss rs) {
        return matchSwissRepestory.save(rs);
    }

    public TreeSet<MatchSwiss> appariementLogic(String num_manche, int idTournois) {
        //je cherche le meilleur joueur : RoundGagner max, roundPerdu MIN , roundNull min

        TreeSet<MatchSwiss> resultSet = new TreeSet<>();
        ArrayList<PlayerSwiss> players = classementTournoisDAO.getPlayerclasser(idTournois) ;
        int numTable = 1;
        while (players.size() > 0) {
            MatchSwiss match = new MatchSwiss();
            String num_match = num_manche + "->" + numTable;
            if (players.size() == 1) {
                resultSet.add(buildPlayerBye(num_manche, players, num_match, numTable));
                players.removeFirst();
            } else {
                PlayerSwiss pW = players.getFirst();
                players.remove(pW);
                PlayerSwiss pB = players.getFirst();
                players.remove(pB);
                match.buildMatch(num_manche, pW.getId(), pW.getNom(), pB.getId(), pB.getNom(), num_match, numTable);
                numTable++;
                match = matchSwissRepestory.save(match);
                resultSet.add(match);
            }
        }
        return resultSet;
    }
}

