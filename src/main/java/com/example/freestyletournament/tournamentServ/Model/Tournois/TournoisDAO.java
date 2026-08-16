package com.example.freestyletournament.tournamentServ.Model.Tournois;

import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwiss;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.ModelService.MancheEnum;
import com.example.freestyletournament.tournamentServ.Model.ModelService.TournoisEnum;
import com.example.freestyletournament.tournamentServ.Model.ModelService.UniqueService;
import com.example.freestyletournament.tournamentServ.Model.PlayerTournois.PlayerTournoisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TournoisDAO {
    @Autowired
    private TournoisModel tournoisM;
    @Autowired
    private MancheSwissDAO mancheSwissDAO;
    @Autowired
    private MatchSwissDAO matchSwissDAO;
    @Autowired
    private PlayerTournoisDAO playerTournoisDAO;
    @Autowired
    private ClassementTournoisDAO classementTournoisDAO;
    private UniqueService us = new UniqueService();
    private static final Logger log = LoggerFactory.getLogger(TournoisDAO.class);

    /**
     * lorsqu'on utilise la methode save de tournoisModel qui implement CRUD de spring
     * il retourne automatique le type de l'entiter ainsi on peux conserver
     * les donner utile si necessaire dans l'entiter
     */
    public Tournois sauverTournois(Tournois tournois) {
        String numTournois = tournois.getNum_tournois();
        if (numTournois == null) {
            tournois.setNum_tournois(us.getNumUnique(false));
            return tournoisM.save(tournois); // INSERT
        } else {
            return tournoisM.save(tournois); //UPDATE
        }
    }
    public String getTournoisName(String num_tournois) {
        return tournoisM.findIdTournoisByNumTournois(num_tournois).getName();
    }

    public TreeSet<MatchSwiss> startTournois(Tournois tournois) {
        Tournois t = getTournoisInfo(tournois);
        TreeSet<MancheSwiss> mancheSet = mancheSwissDAO.creerManche(t.getNum_tournois(), t.getNbr_manche(), t.getId());
        log.info("lenght SEt = " + mancheSet.size());
        MancheSwiss m = mancheSet.getFirst();
        mancheSwissDAO.sauverStatusManche(m,MancheEnum.ENCOURS.getTyni());
        log.info("MANCHE =" + m.getNum_manche());
        classementTournoisDAO.initialiserClassement(t.getId(), playerTournoisDAO.getPlayerByIdTournois(t.getId()));
        return matchSwissDAO.appariementAleatoir(m.getNum_manche(), playerTournoisDAO.getPlayerByIdTournois(t.getId()));
    }

    private Tournois getTournoisInfo(Tournois tournois) {
        log.info("tournois num :" + tournois.getNum_tournois() + " " + tournois.getDuree_min());
        Tournois t = tournoisM.findIdTournoisByNumTournois(tournois.getNum_tournois());
        log.info("tournois :" + t.toString());
        t.setStatusTournois(TournoisEnum.ENCOUR.getTyni());
        t.setName(tournois.getName());
        t.setCadence(tournois.getCadence());
        t.setDuree_min(tournois.getDuree_min());
        t.setNbr_manche(tournois.getNbr_manche());
        t = tournoisM.save(t);
        return t;
    }

    public TreeSet<Tournois> getAllTournois() {
        TreeSet<Tournois> setTournois = new TreeSet<Tournois>();
        for (Tournois t : tournoisM.findAll()) {
            setTournois.add(t);
        }
        return setTournois;
    }

    public TreeSet<MatchSwiss> nextManche(TreeSet<MatchSwiss> matches) throws DernierMancheException {
        //enregistrer status de manche precedenent
        //preparer une nouvelle round avec les meme joueur et le idtournois
        //renvoyer une liste de match
        String num_manchePrecedente = matches.getFirst().getNum_manche();
        int idTournois = mancheSwissDAO.getIdTournois(num_manchePrecedente);
        log.info("TournDaO::nextManche::" + "manche precedente : " + num_manchePrecedente);
        mancheSwissDAO.sauverStatusManche(mancheSwissDAO.getMancheByNum(num_manchePrecedente), MancheEnum.FINI.getTyni());
        for (MatchSwiss match : matches) {
            matchSwissDAO.changerStatus(match);
            classementTournoisDAO.sauverPoint(idTournois, match);
        }
        MancheSwiss m = new MancheSwiss();
        m = mancheSwissDAO.getNextManche(mancheSwissDAO.getMancheByNum(num_manchePrecedente));
        return matchSwissDAO.appariementLogic(m.getNum_manche(), idTournois);
    }

    public TournoisDTO getTournoisDto(String num_manche){
        log.info("NumManche pour DTO : "+num_manche);
        Tournois t = tournoisM.findTournoisByNumManche(num_manche);
        return new TournoisDTO(t.getNbr_manche(),t.getName(),t.getNum_tournois(),t.getStatusTournois());
    }
}
