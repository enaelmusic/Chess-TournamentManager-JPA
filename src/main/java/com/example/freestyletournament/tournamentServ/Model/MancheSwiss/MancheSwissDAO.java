package com.example.freestyletournament.tournamentServ.Model.MancheSwiss;

import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwiss;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.ModelService.MancheEnum;
import com.example.freestyletournament.tournamentServ.Model.ModelService.MatchStatus;
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
    private MancheSwissRep mancheRep;
    @Autowired
    private TournoisModel tournoisModel;
    @Autowired
    private MatchSwissDAO matchSwissDAO;

    public TreeSet<MancheSwiss>  creerManche(String num_tournois, int nbrManche, int idTournois){
        log.info("je rentre en fonction creerManche numtournois= "+num_tournois+"nbrmanche = "+nbrManche);
        TreeSet<MancheSwiss> set= new TreeSet<MancheSwiss>();

        for(int i=0; i<nbrManche ; i++){
            log.info("boucle i="+i);
            MancheSwiss m = new MancheSwiss();
            m.setNum_tournois(num_tournois);
            m.setNum_manche("T-"+idTournois+"-Manche-"+(i+1)+"-"+ LocalDateTime.now());
            m.setInt_manche(i+1);
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
    private MancheSwiss jourOUTsauverStatusManche(String num_manche){
        MancheSwiss manche = mancheRep.findMancheByManche(num_manche);
        manche.setStatus(MancheEnum.ANNULE.getTyni());
        manche = mancheRep.save(manche);
        matchSwissDAO.annulerMatch(num_manche,manche.getStatus());
        return manche;
    }

    public void joueurOutService(int idTournois) {
        String numManche = matchSwissDAO.dernierMancheEnCours(idTournois);
        MancheSwiss manche = jourOUTsauverStatusManche(numManche);
        matchSwissDAO.appariementPlayer(numManche, idTournois,manche.getInt_manche());
        sauverStatusManche(manche,MancheEnum.ENCOURS.getTyni());
    }

    public int getIdTournois(String numManche){
        log.info("MSDAO :"+numManche);
        MancheSwiss mancheSwiss=mancheRep.findMancheByManche(numManche);
        Tournois tournois = tournoisModel.findIdTournoisByNumTournois(mancheSwiss.getNum_tournois());
        return tournois.getId();
    }

    public MancheSwiss getMancheByNum(String num_manche){
        return mancheRep.findMancheByManche(num_manche);
    }

    public ArrayList<MancheSwiss> getManchesByTournoisNum(String num_tournois){
        return  mancheRep.findAllMancheByTournois(num_tournois);
    }
    public ArrayList<MancheSwiss> getManchesByTournoisId(String idTournois){
        log.info("idTournois->",idTournois);
        int idInt = Integer.parseInt(idTournois);
        String numTournois = tournoisModel.findIdTournoisByiDtournois(idInt).getNum_tournois();
        return  mancheRep.findAllMancheByTournois(numTournois);
    }
}
