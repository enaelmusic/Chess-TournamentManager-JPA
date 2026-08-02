package com.example.freestyletournament.tournamentServ.Model.MancheSwiss;

import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwiss;
import com.example.freestyletournament.tournamentServ.Model.Tournois.DernierMancheException;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

@Service
public class MancheSwissDAO {
    private static final Logger log= LoggerFactory.getLogger(MancheSwissDAO.class);
    @Autowired
    MancheSwissRep mancheRep;
    @Autowired
    TournoisModel tournoisModel;

    public TreeSet<MancheSwiss>  creerManche(String num_tournois, int nbrManche, int idTournois){
        log.info("je rentre en fonction creerManche numtournois= "+num_tournois+"nbrmanche = "+nbrManche);
        TreeSet<MancheSwiss> set= new TreeSet<MancheSwiss>();

        for(int i=0; i<nbrManche ; i++){
            log.info("boucle i="+i);
            MancheSwiss m = new MancheSwiss();
            m.setNum_tournois(num_tournois);
            m.setNum_manche("T-"+idTournois+"-Manche-"+(i+1)+"-"+ LocalDateTime.now());
            mancheRep.save(m);
            set.add(m);
        }
        return set;
    }

    public MancheSwiss getNextManche(MancheSwiss manche) throws DernierMancheException {
        String num_tournois = manche.getNum_tournois();
        MancheSwiss m = new MancheSwiss();
        ArrayList<MancheSwiss> manches = mancheRep.findAllMancheByTournois(num_tournois);
        for(int i=0;i<manches.size();i++){
            if(manches.get(i).getNum_manche().equals(manche.getNum_manche())){
                log.info("manches precedente : "+manches.get(i).getNum_manche()+"valeur i="+i+"size :"+manches.size());
    //            log.info("manches precedente : "+manches.get(i).getNum_manche());
                if(i==(manches.size()-1)){
                    throw new DernierMancheException();
                }
                else{
                    m= manches.get(i+1);
                    log.info("manches suivante : "+m.getNum_manche());
                    break;
                }
            }
        }
        return  m;
    }

    public MancheSwiss sauverStatusManche(MancheSwiss manche , int x){
        manche.setStatus(x);
        return mancheRep.save(manche);
    }

    public int getIdTournois(String numManche){
        log.info("MSDAO :"+numManche);
        MancheSwiss mancheSwiss=mancheRep.findIdMancheByManche(numManche);
        Tournois tournois = tournoisModel.findIdTournoisByNumTournois(mancheSwiss.getNum_tournois());
        return tournois.getId();
    }

    public MancheSwiss getMancheByNum(String num_manche){
        return mancheRep.findIdMancheByManche(num_manche);
    }

    public ArrayList<MancheSwiss> getManchesByTournoisNum(String num_tournois){
        return  mancheRep.findAllMancheByTournois(num_tournois);
    }
}
