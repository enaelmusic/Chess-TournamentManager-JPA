package com.example.freestyletournament.tournamentServ.Model.PlayerTournois;

import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissRepestory;
import com.example.freestyletournament.tournamentServ.Model.ModelService.MancheEnum;
import com.example.freestyletournament.tournamentServ.Model.ModelService.ParticipantEnum;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissDTO;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissModel;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
public class PlayerTournoisDAO {
    @Autowired
    private PlayerTournoisModel playerTournoiModel;

    private static final Logger log = LoggerFactory.getLogger(PlayerTournoisDAO.class);
    @Autowired
    private PlayerSwissModel psModel;
    @Autowired
    private MatchSwissDAO matchSwissDAO;
    @Autowired
    private MatchSwissRepestory matchSwissRepestory;
    @Autowired
    private MancheSwissDAO mancheSwissDAO;

    public void savePlayerTournois(int idTournois, int idPlayer){
        PlayerTournois pt = new PlayerTournois();
        pt.setIdTournois(idTournois);
        pt.setIdPlayer(idPlayer);
        playerTournoiModel.save(pt);
    }
    public void saveJoueurOutTournois(PlayerTournois playerTournois){
        PlayerTournois pt = playerTournoiModel.findPlayerByIdPlayerAndTournois(playerTournois.getIdPlayer(),playerTournois.getIdTournois());
        pt.setStatus(ParticipantEnum.PARTICIPEPLUS.getTyni());
        playerTournoiModel.save(pt);
        int idTournois = pt.getIdTournois();
        mancheSwissDAO.joueurOutService(idTournois);
    }

    public TreeSet<PlayerSwissDTO> getPlayerByIdTournois(int idTournois){
        TreeSet< PlayerSwissDTO> playerSet = new TreeSet<PlayerSwissDTO>();
        Set<PlayerTournois> playerTournois = playerTournoiModel.findAllByIdTournois(idTournois);
        for(PlayerTournois pt : playerTournois){
            if(pt.getStatus()!=ParticipantEnum.PARTICIPEPAS.getTyni()){
                log.info("playertournois status: "+pt.getStatus());
                PlayerSwiss player = psModel.findById_joueurJ(pt.getIdPlayer());
                PlayerSwissDTO pDTO = new PlayerSwissDTO(player.getId(),player.getNom(), pt.getStatus());
                log.info("PlayerDTO status : "+pDTO.getStatusTournois());
                playerSet.add(pDTO);
            }
        }
        return playerSet;
    }

    public void supprimerParticipant(int idPlayer) {
        log.info("SUPPRIMER PARTICIPANT", idPlayer);
        PlayerTournois p = playerTournoiModel.findPlayerById(idPlayer);
        p.setStatus(ParticipantEnum.PARTICIPEPAS.getTyni());
        playerTournoiModel.save(p);
        log.info("Le participant a été supprimer son status est = "+p.getStatus());
    }
}
