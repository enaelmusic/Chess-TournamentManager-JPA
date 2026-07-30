package com.example.freestyletournament.tournamentServ.Model.PlayerSwiss;

import com.example.freestyletournament.tournamentServ.Model.ModelService.UniqueService;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class PlayerSwissDAO {
    @Autowired
    private PlayerSwissModel playerSwissModel;

    private UniqueService us = new UniqueService();
    public PlayerSwiss sauverPlayer(PlayerSwiss playerSwiss){
        String num_joueur = playerSwiss.getNum_joueur();
        if(num_joueur==null){
            playerSwiss.setNum_joueur(us.getNumUnique(true));
            return playerSwissModel.save(playerSwiss);
        }
        else{
            return playerSwissModel.save(playerSwiss);
        }
    }

    public PlayerSwiss getPlayer(String num_joueur){
        PlayerSwiss player =playerSwissModel.findByNum_joueur(num_joueur);
        return player;
    }
}
