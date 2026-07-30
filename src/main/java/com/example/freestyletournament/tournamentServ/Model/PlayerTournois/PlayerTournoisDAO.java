package com.example.freestyletournament.tournamentServ.Model.PlayerTournois;

import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissModel;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class PlayerTournoisDAO {
    @Autowired
    private PlayerTournoisModel playerTournoiModel;

    @Autowired
    private PlayerSwissModel psModel;

    public void savePlayerTournois(int idTournois, int idPlayer){
        PlayerTournois pt = new PlayerTournois();
        pt.setIdTournois(idTournois);
        pt.setIdPlayer(idPlayer);
        playerTournoiModel.save(pt);
    }

    public TreeSet<PlayerSwiss> getPlayerByIdTournois(int idTournois){
        TreeSet< PlayerSwiss> playerSet = new TreeSet<PlayerSwiss>();
        Set<Integer> playerTournois = playerTournoiModel.findAllByIdTournois(idTournois);
        for(int pt : playerTournois){
            Optional<PlayerSwiss> player = psModel.findById(pt);
            playerSet.add(player.get());
        }
        return playerSet;
    }

}
