package com.example.freestyletournament.tournamentServ.Model.ClassementTournois;

import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwiss;
import com.example.freestyletournament.tournamentServ.Model.ModelService.ParticipantEnum;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.TreeSet;

@Service
public class ClassementTournoisDAO {
    private Logger log = LoggerFactory.getLogger(ClassementTournoisDAO.class);
    @Autowired
    private ClassementTournoisRep classement;

    public void sauverPoint(int idTournois, MatchSwiss match){
        int statusMatch = match.getStatus();
        log.info("idTournois : "+idTournois+" idPlayerW"+match.getIdPlayerW()+" idBlack :"+match.getIdPlayerB());
        ClassementTournois classementPlayerW = classement.findClassementByIdTournoisAndIdPlayer(idTournois,match.getIdPlayerW());
        ClassementTournois classementPlayerB = classement.findClassementByIdTournoisAndIdPlayer(idTournois,match.getIdPlayerB());

        switch (statusMatch){
            case 1:
                classementPlayerW.setPoint(classementPlayerW.getPoint()+3);
                classementPlayerW.setRound_gagner(classementPlayerW.getRound_gagner()+1);
                classementPlayerB.setRound_perdu(classementPlayerB.getRound_perdu()+1);
                break;
            case 2:
                classementPlayerB.setPoint(classementPlayerB.getPoint()+3);
                classementPlayerB.setRound_gagner(classementPlayerB.getRound_gagner()+1);
                classementPlayerW.setRound_perdu(classementPlayerW.getRound_perdu()+1);
                break;
            case 3:
                classementPlayerB.setPoint(classementPlayerB.getPoint()+1);
                classementPlayerW.setPoint(classementPlayerW.getPoint()+1);
                classementPlayerB.setRound_null(classementPlayerB.getRound_null()+1);
                classementPlayerW.setRound_null(classementPlayerW.getRound_null()+1);
                break;
            case 4:
                classementPlayerB= classementPlayerW;
                classementPlayerW.setPoint(classementPlayerW.getPoint()+1);
                break;
        }
        classement.save(classementPlayerW);
        classement.save(classementPlayerB);
    }
    public void initialiserClassement(int idTournois, TreeSet<PlayerSwissDTO> players){
        log.info("CLASSMENT INIT START : size players= "+players.size());
        for(PlayerSwissDTO p: players){
            ClassementTournois ct = new ClassementTournois();
            ct.setIdTournois(idTournois);
            ct.setIdPlayer(p.getId());
            classement.save(ct);
            log.info("joueur ajouter au classement : "+p.getId());
        }
    }

    public PlayerSwiss findMeilleurJoueur() {
        return classement.findMeilleurJoueur();
    }

    public PlayerSwiss findAdversaire(int idPlayer) {
        return classement.findAdversaireQuery(idPlayer);
    }

    public ArrayList<PlayerSwissDTO> getPlayerclasser(int idTournois){
        ArrayList<PlayerSwissDTO>  result= classement.getPlayersClassee(idTournois);
        for(int i= result.size(); i>0;i--){
            PlayerSwissDTO p = result.get(i-1);
            if(p.getStatusTournois()== ParticipantEnum.PARTICIPEPAS.getTyni() || p.getStatusTournois()==ParticipantEnum.PARTICIPEPLUS.getTyni()){
                result.remove(i-1);
            }
        }
        return result;
    }
    public ArrayList<ClassementDTO> getClassmenetTournois(String idTournois){
        return classement.getClassementTournois(idTournois);
    }
}
