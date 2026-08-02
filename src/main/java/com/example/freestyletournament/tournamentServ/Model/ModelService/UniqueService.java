package com.example.freestyletournament.tournamentServ.Model.ModelService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UniqueService {
    private String numUnique;
    private static int compteurU=1000;
    private LocalDate datetime;

    /**
     *
     * @param playerORtournois si c'est faux c'est un tournois si c'est vrais c'est un joueur.
     * @return le numero unique en fonction de joueur, organisateur ou tournois
     */
    public String getNumUnique(boolean playerORtournois){
        String datenow= LocalDateTime.now().toString().replace(":","");
        compteurU++;
        if(!playerORtournois){
            numUnique="T-"+datenow+"-"+compteurU;
        }
        else {

            numUnique="P-"+datenow+"-"+compteurU;
        }
        return  numUnique;
    }
    public String getNumMM(boolean mancheORmatch, int num_manche, int idMatch){
        if(!mancheORmatch){
            return "M"+num_manche+"-"+idMatch;
        }
        else{
            return "T"+num_manche+"-"+idMatch;
        }
    }
}
