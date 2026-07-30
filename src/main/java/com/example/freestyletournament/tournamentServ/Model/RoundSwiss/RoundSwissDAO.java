package com.example.freestyletournament.tournamentServ.Model.RoundSwiss;

import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
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
public class RoundSwissDAO {
    @Autowired
    RoundSwissRepestory roundSwissRep;
    @Autowired
    TournoisModel tournoisM;
    private static final Logger log =  LoggerFactory.getLogger(TournoisDAO.class);
    private final Random rand = new Random();
    public TreeSet<RoundSwiss> appariementAleatoir(int id, TreeSet<PlayerSwiss> playerSwisses) {
        Tournois tournois = tournoisM.findIdTournoisByiDtournois(id);
        log.info("tournois trouver id= " + tournois.getId());
        TreeSet<RoundSwiss> resultSet = new TreeSet<RoundSwiss>();
        ArrayList<PlayerSwiss> players = new ArrayList<>();
        for (PlayerSwiss ps : playerSwisses) {
            players.add(ps);
        }
        int numTable=1;
        while (players.size() > 0) {
            RoundSwiss rs = new RoundSwiss();
            if (players.size() == 1) {
                resultSet.add(buildPlayerBye(tournois,players));
                players.removeFirst();
            } else {
                int randint = rand.nextInt(1, players.size());
                log.info("randint = " + randint + "player size =" + players.size());
                PlayerSwiss pW = players.get(randint);
                players.remove(randint);
                randint = rand.nextInt(1, players.size());
                PlayerSwiss pB = players.get(randint);
                players.remove(randint);
                rs.buildRS(tournois.getNum_tournois(), pW.getId(), pW.getNom(), pB.getId(), pB.getNom());
                rs.setTableNum(numTable);
                roundSwissRep.save(rs);
                resultSet.add(rs);
                numTable++;
            }
        }
        return resultSet;
    }

    public RoundSwiss buildPlayerBye(Tournois tournois,ArrayList<PlayerSwiss> players){
        RoundSwiss rs = new RoundSwiss();
        PlayerSwiss pW = players.getFirst();
        rs.buildRS(tournois.getNum_tournois(), pW.getId(), pW.getNom(), pW.getId(), "BYE");
        rs.setStatus(4);
        rs.setTableNum(0);
        roundSwissRep.save(rs);
        return rs;
    }
    public RoundSwiss sauverResultatRound (RoundSwiss rs,int result){
            rs.setStatus(result);
            return roundSwissRep.save(rs);
        }
    }
