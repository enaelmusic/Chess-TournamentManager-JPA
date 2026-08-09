package com.example.freestyletournament.tournamentServ.Model.PlayerSwiss;

import com.example.freestyletournament.tournamentServ.Model.ModelService.UniqueService;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class PlayerSwissDAO {
    private static final Logger log = LoggerFactory.getLogger(PlayerSwissDAO.class);
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

    public PlayerSwiss getPlayer(int id){
        log.info("je recois l'id pour le nom joueur : {}",id);
        PlayerSwiss player =playerSwissModel.findById_joueurJ(id);
        log.info("je renvoie le player id = {}",player.getNom());
        return player;
    }
}
