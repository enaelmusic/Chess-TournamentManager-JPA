package com.example.freestyletournament.tournamentServ.Model.Tournois;

import com.example.freestyletournament.tournamentServ.Model.ModelService.UniqueService;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissModel;
import com.example.freestyletournament.tournamentServ.Model.PlayerTournois.PlayerTournoisModel;
import com.example.freestyletournament.tournamentServ.Model.RoundSwiss.RoundSwiss;
import com.example.freestyletournament.tournamentServ.Model.RoundSwiss.RoundSwissRepestory;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;

@Service
public class TournoisDAO {

    private static final Logger log =  LoggerFactory.getLogger(TournoisDAO.class);
    @Autowired
    private TournoisModel tournoisM;

    @Autowired
    private PlayerTournoisModel playTournois ;
    @Autowired
    RoundSwissRepestory roundSwissRep;
    private UniqueService us = new UniqueService();
    /**
     * lorsqu'on utilise la methode save de tournoisModel qui implement CRUD de spring
     * il retourne automatique le type de l'entiter ainsi on peux conserver
     * les donner utile si necessaire dans l'entiter
     */
    public  Tournois sauverTournois(Tournois tournois){
        String numTournois = tournois.getNum_tournois();
        if(numTournois==null){
            tournois.setNum_tournois(us.getNumUnique(false));
             return tournoisM.save(tournois);
        }
        else{
             return tournoisM.save(tournois);
        }
    }

    public String getTournoisName(Tournois tournois){
        return tournois.getName();
    }

    public TreeSet<Tournois> getTournois(){
        TreeSet<Tournois> setTournois = new TreeSet<Tournois>();
        for (Tournois t: tournoisM.findAll()
             ) {
            setTournois.add(t);
        }
        return setTournois;
    }
}
