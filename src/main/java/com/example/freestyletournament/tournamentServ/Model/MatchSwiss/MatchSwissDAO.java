package com.example.freestyletournament.tournamentServ.Model.MatchSwiss;

import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementTournoisRep;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwissRep;
import com.example.freestyletournament.tournamentServ.Model.ModelService.MatchStatus;
import com.example.freestyletournament.tournamentServ.Model.ModelService.ParticipantEnum;
import com.example.freestyletournament.tournamentServ.Model.ModelService.UniqueService;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissDTO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisModel;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchSwissDAO {
    @Autowired
    MatchSwissRepestory matchSwissRepestory;
    @Autowired
    ClassementTournoisRep classementTournoisRep;
    @Autowired
    ClassementTournoisDAO classementTournoisDAO;
    @Autowired
    TournoisModel tournoisM;
    @Autowired
    private MancheSwissRep mancheSwissRep;
    private UniqueService us = new UniqueService();
    private static final Logger log = LoggerFactory.getLogger(TournoisDAO.class);
    private final Random rand = new Random();

    public TreeSet<MatchSwiss> appariementAleatoir(String num_manche, TreeSet<PlayerSwissDTO> playerSwisses) {
        log.info("je rentre en fonction appariemnt aleatoir: " + num_manche);
        // MancheSwiss manche = mancheSwissRep.findIdMancheByManche(num_manche);
//        log.info("manche trouver id= " + manche.getId());
        TreeSet<MatchSwiss> resultSet = new TreeSet<MatchSwiss>();
        ArrayList<PlayerSwissDTO> players = new ArrayList<>();
        for (PlayerSwissDTO ps : playerSwisses) {
            players.add(ps);
        }
        int numTable = 1;
        while (players.size() > 0) {
            MatchSwiss match = new MatchSwiss();
            String num_match = num_manche + "->" + numTable;
            if (players.size() == 1) {
                resultSet.add(buildPlayerBye(num_manche, players, num_match, numTable));
                players.removeFirst();
            } else {
                int randint = rand.nextInt(players.size());
                log.info("randint = " + randint + "player size =" + players.size());
                PlayerSwissDTO pW = players.get(randint);
                players.remove(randint);
                randint = rand.nextInt(players.size());
                PlayerSwissDTO pB = players.get(randint);
                players.remove(randint);

                match.buildMatch(num_manche, pW.getId(), pW.getNom(), pB.getId(), pB.getNom(), num_match, numTable);
                numTable++;
                log.info("num manche : " + match.getNum_manche());
                match = matchSwissRepestory.save(match);
                resultSet.add(match);
            }
        }
        log.debug("fin d'pairing aleatoir");
        return resultSet;
    }

    public MatchSwiss buildPlayerBye(String num_manche, ArrayList<PlayerSwissDTO> players, String num_match, int numTable) {
        MatchSwiss match = new MatchSwiss();
        PlayerSwissDTO pW = players.getFirst();
        match.buildMatch(num_manche, pW.getId(), pW.getNom(), pW.getId(), "BYE", num_match, numTable);
        match.setStatus(MatchStatus.BYE.getTyni());
        match.setTableNum(0);
        matchSwissRepestory.save(match);
        return match;
    }
    public MatchSwiss buildPlayerBye(String num_manche, PlayerSwissDTO player, String num_match, int numTable) {
        MatchSwiss match = new MatchSwiss();
        match.buildMatch(num_manche, player.getId(), player.getNom(), player.getId(), "BYE", num_match, numTable);
        match.setStatus(MatchStatus.BYE.getTyni());
        return match;
    }

    public MatchSwiss changerStatus(MatchSwiss rs) {
        return matchSwissRepestory.save(rs);
    }
    private Map<Integer,Set<Integer>> historiqueMapConvertor(TreeSet<MatchSwissDTO> historiqueRencontre ){
        Map<Integer, Set<Integer>> adversaireHistorique = new HashMap<>();
        for(MatchSwissDTO m : historiqueRencontre){
            int w= m.getIdPlayerW();
            int b = m.getIdPlayerB();
            if(!adversaireHistorique.containsKey(w)){
                adversaireHistorique.put(w,new HashSet<>());
            }
            if(!adversaireHistorique.containsKey(b)){
                adversaireHistorique.put(b, new HashSet<>());
            }
            adversaireHistorique.get(w).add(b);
            adversaireHistorique.get(b).add(w);
        }
        return adversaireHistorique;
    }
    public TreeSet<MatchSwiss> appariementPlayer(String num_manche, int idTournois){
        TreeSet<MatchSwiss> result = new TreeSet<>();
        ArrayList<PlayerSwissDTO> particpants = classementTournoisDAO.getPlayerclasser(idTournois);
        TreeSet<MatchSwissDTO> historiqueRencontre = matchSwissRepestory.chargerHistorique(idTournois);
        Map<Integer, Set<Integer>> adversaireHistorique = historiqueMapConvertor(historiqueRencontre);
        ArrayList<MatchSwiss> matchsTemporaires = new ArrayList<>();
        int num_table=1;
        for(Map.Entry<Integer, Set<Integer>> ligne : adversaireHistorique.entrySet()){
            System.out.println("le joueur : "+ligne.getKey()+" a jouer contre : "+ligne.getValue());

        }
        if(particpants.size()%2!=0){
            return gestionImpaire(num_manche,particpants, adversaireHistorique, matchsTemporaires, num_table, result);
        }
        else{
            return gestionPaire(num_manche,particpants, adversaireHistorique, matchsTemporaires, num_table, result);
        }
    }

    private TreeSet<MatchSwiss> gestionPaire(String num_manche,  ArrayList<PlayerSwissDTO> particpants, Map<Integer, Set<Integer>> adversaireHistorique, ArrayList<MatchSwiss> matchsTemporaires, int num_table, TreeSet<MatchSwiss> result) {
        boolean appariementSucces = chercherAdversaire(num_manche, particpants, adversaireHistorique, matchsTemporaires, num_table);
        if(appariementSucces){
            for(MatchSwiss m : matchsTemporaires){
                result.add(matchSwissRepestory.save(m));
            }
            return result;
        }
        else{
            throw new IllegalStateException("probleme d'appareiment");
        }
    }

    private TreeSet<MatchSwiss> gestionImpaire(String num_manche, @NonNull ArrayList<PlayerSwissDTO> particpants, Map<Integer, Set<Integer>> adversaireHistorique, ArrayList<MatchSwiss> matchsTemporaires, int num_table, TreeSet<MatchSwiss> result) {
        for(int i = particpants.size(); i>0; i--){
            int idParticipant = particpants.get(i-1).getId();
            boolean etaitBye = adversaireHistorique.containsKey(idParticipant) && adversaireHistorique.get(idParticipant).contains(idParticipant);
            if(!etaitBye){
                PlayerSwissDTO playerDernier = particpants.remove(i-1);
                boolean appariementSucces = chercherAdversaire(num_manche, particpants, adversaireHistorique, matchsTemporaires, num_table);
                if(appariementSucces){
                    int derniereTable = matchsTemporaires.size()+1;
                    String num_match = num_manche + "->" + derniereTable;
                    MatchSwiss matchBy = buildPlayerBye(num_manche, playerDernier, num_match, derniereTable);
                    matchsTemporaires.add(matchBy);
                    for(MatchSwiss matchSwiss : matchsTemporaires){
                        result.add(matchSwissRepestory.save(matchSwiss));
                    }
                    return result;
                }
                else{
                    particpants.add(i-1,playerDernier);
                }
            }
        }
        throw new IllegalStateException("probleme d'appariement");
    }

    /**
     * chercherAdversaire
     * @param num_manche
     * @param particpants
     * @param adversaireHistorique
     * @param matchsTemporaires
     * @param numTable
     * @return si tout les joueurs ont etes apparier, ou que par recurssion la liste c'est videe induit que tout les joueurs ont ete apparier
     */
    private boolean chercherAdversaire(String num_manche,
                                       ArrayList<PlayerSwissDTO> particpants,
                                       Map<Integer, Set<Integer>> adversaireHistorique,
                                       ArrayList<MatchSwiss> matchsTemporaires,
                                       int numTable) {
        if(particpants.isEmpty()){
            return true;
        }
        PlayerSwissDTO playerWhite = particpants.removeFirst();
        for(int i=0; i<particpants.size();i++){
            PlayerSwissDTO playerBlackPotentiel = particpants.get(i);
            Set<Integer> adversaires = adversaireHistorique.get(playerWhite.getId());
            boolean dejaAffronter= (adversaires!=null)&&(adversaires.contains(playerBlackPotentiel.getId()));
            if(!dejaAffronter){
                PlayerSwissDTO playerBlack = particpants.remove(i);
                String num_match = num_manche + "->" + numTable;
                MatchSwiss m = new MatchSwiss();
                m.buildMatch(num_manche,playerWhite.getId(),playerWhite.getNom(),playerBlack.getId(),playerBlack.getNom(),num_match,numTable);
                matchsTemporaires.add(m);
                /**
                 * si cette fonctionne donne vrai, toute les tables on été faite(participants.isEmpty()=true;)
                 * on imbrique la methode complete dans la boucle jusqu'a ce que ca deviennent true.
                 */
                if(chercherAdversaire(num_manche,particpants,adversaireHistorique,matchsTemporaires,numTable+1)){
                    return true;
                }
                //si le true ne viens pas, alors on annule le match enregistré et remet le player black dans la liste.
                matchsTemporaires.removeLast();
                particpants.add(i,playerBlack);
            }
        }
        //si il ne peux pas appareier il repond faux. un joueur bye doit etre construit, ou que les joueur ce sont déja rencontrer.
        particpants.addFirst(playerWhite);
        return false;
    }
    public TreeSet<MatchSwiss> getMatchByManche(String num_manche){
        TreeSet<MatchSwiss> m = matchSwissRepestory.findMatchBynumManche(num_manche);
        return m;
    }
    public TreeSet<MatchSwissDTO> chargerHistorique(int i) {
        return matchSwissRepestory.chargerHistorique(i);
    }
}