package com.example.freestyletournament.tournamentServ;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwiss;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissDTO;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissRepestory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.Map.*;

@SpringBootTest
public class GetterTest {
    @Autowired
    private MatchSwissDAO matchSwissDAO;
    @Test
    void getHistorique(){
        System.out.println("je rentre en test");
        TreeSet<MatchSwissDTO> matchs = matchSwissDAO.chargerHistorique(208);
        if(matchs==null){
            System.out.println("La collection map est vide");
        }
        else{
            for(MatchSwissDTO m : matchs){
                System.out.println("idRec : "+m.getId()+" mancheInt : "+m.getInt_manche()+" idPlayerW: "+m.getIdPlayerW()+" idPlayer b : "+m.getIdPlayerB());
            }
        }
    }
}
