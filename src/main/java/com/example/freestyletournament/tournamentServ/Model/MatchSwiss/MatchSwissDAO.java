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
    private Map<Integer, Map<Integer,Set<Integer>>> historiqueMapConvertor(TreeSet<MatchSwissDTO> historiqueRencontre ){
        /**
         * @var adversaireHistorique Map<idJoueur, numMancheInt, idAdversaire>
         *     historique permettant le controle des revanches
         * @var w player blanc-white
         * @var b player noir-black
         * @var n numero entier manche
         */
        Map<Integer, Map<Integer,Set<Integer>>> adversaireHistorique = new HashMap<Integer, Map<Integer,Set<Integer>>>();
        for(MatchSwissDTO m : historiqueRencontre){
            int w= m.getIdPlayerW();
            int b = m.getIdPlayerB();
            int n= m.getInt_manche();
            if(!adversaireHistorique.containsKey(w)){
                adversaireHistorique.put(w,new HashMap<Integer,Set<Integer>>());
            }
            if(!adversaireHistorique.containsKey(b)){
                adversaireHistorique.put(b, new HashMap<Integer,Set<Integer>>());
            }
            if(!adversaireHistorique.get(w).containsKey(b)){
                Set<Integer> manches = new HashSet<>();
                manches.add(n);
                adversaireHistorique.get(w).put(b,manches);
            }
            if(!adversaireHistorique.get(b).containsKey(w)){
                Set<Integer> manches = new HashSet<>();
                manches.add(n);
                adversaireHistorique.get(b).put(w,manches);
            }
            adversaireHistorique.get(w).get(b).add(n);
            adversaireHistorique.get(b).get(w).add(n);
        }
        return adversaireHistorique;
    }
    public TreeSet<MatchSwiss> appariementPlayer(String num_manche, int idTournois, int mancheInt) throws AppariementException{
        TreeSet<MatchSwiss> result= new TreeSet<>();
        ArrayList<PlayerSwissDTO> particpants = classementTournoisDAO.getPlayerclasser(idTournois);
        TreeSet<MatchSwissDTO> historiqueRencontre = matchSwissRepestory.chargerHistorique(idTournois);
        Map<Integer, Map<Integer,Set<Integer>>> adversaireHistorique = historiqueMapConvertor(historiqueRencontre);
        ArrayList<MatchSwiss> matchsTemporaires = new ArrayList<>();
        int num_table=1;
        boolean impaire = particpants.size()%2!=0;
        for(Map.Entry<Integer,Map<Integer, Set<Integer>>> mapPrincipale :adversaireHistorique.entrySet()){
            for(Map.Entry<Integer, Set<Integer>> ligne : mapPrincipale.getValue().entrySet()){
                System.out.println("le joueur : "+ mapPrincipale.getKey()+ " a jouer contre : " +ligne.getKey() + "aux manche : " + ligne.getValue().toString());
            }
        }
        for(int niveauxException = 0 ; niveauxException<=2;niveauxException++){
            try{
                if(impaire) {
                    return gestionImpaire(num_manche,particpants, adversaireHistorique, matchsTemporaires, num_table, result,mancheInt, niveauxException);
                }
                return gestionPaire(num_manche,particpants, adversaireHistorique, matchsTemporaires, num_table, result, mancheInt, niveauxException);
            } catch (AppariementException e) {
                //aucun retour fait le niveau augmente si i est endessous de 3
            }
        }
        //si aucun retour n'est fait, la boucle s'arrete à 2. il y a eu un probleme avec l'appariement du à un joueur BYE 2 fois
        if(impaire){
            return gestionException(num_manche,particpants,adversaireHistorique,matchsTemporaires,num_table,result,mancheInt,2);
        }
        throw new AppariementException("Appariement impossible, exception inconnue");
    }

    private TreeSet<MatchSwiss> gestionException(String num_manche,
                                                 @NonNull ArrayList<PlayerSwissDTO> particpants,
                                                 Map<Integer, Map<Integer,Set<Integer>>> adversaireHistorique,
                                                 ArrayList<MatchSwiss> matchsTemporaires, int numTable,
                                                 TreeSet<MatchSwiss> result, int mancheInt, int niveau) throws AppariementException{
        for(int i = particpants.size(); i>0; i--){
            int idParticipant = particpants.get(i-1).getId();
            Map<Integer,Set<Integer>> adversairePropre = adversaireHistorique.get(idParticipant);
            boolean etaitBye = adversaireHistorique.containsKey(idParticipant) && adversairePropre.containsKey(idParticipant);
            /**
             * expection 1/
             *     le joueur est deja bye et que la taille du set est different de la manche precedente(un joueur à déclaré forfait durant le tournois)
             */
            if(etaitBye){
                PlayerSwissDTO playerDernier = particpants.remove(i-1);
                boolean appariementSucces = chercherAdversaire(num_manche, particpants, adversaireHistorique, matchsTemporaires, numTable,mancheInt,niveau);
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
        throw new AppariementException("probleme d'appariement");
    }

    private TreeSet<MatchSwiss> gestionPaire(String num_manche,
                                             ArrayList<PlayerSwissDTO> particpants,
                                             Map<Integer, Map<Integer,Set<Integer>>> adversaireHistorique,
                                             ArrayList<MatchSwiss> matchsTemporaires,
                                             int num_table,
                                             TreeSet<MatchSwiss> result, int mancheInt,
                                             int niveau) throws AppariementException {
        boolean appariementSucces = chercherAdversaire(num_manche, particpants, adversaireHistorique, matchsTemporaires, num_table, mancheInt, niveau);
        if(appariementSucces){
            for(MatchSwiss m : matchsTemporaires){
                result.add(matchSwissRepestory.save(m));
            }
            return result;
        }
        else{
            throw new AppariementException("Erreur d'appariement");
        }
    }

    private TreeSet<MatchSwiss> gestionImpaire(String num_manche,
                                               @NonNull ArrayList<PlayerSwissDTO> particpants,
                                               Map<Integer, Map<Integer,Set<Integer>>> adversaireHistorique,
                                               ArrayList<MatchSwiss> matchsTemporaires,
                                               int num_table, TreeSet<MatchSwiss> result,
                                               int mancheInt, int niveau) throws AppariementException {
        for(int i = particpants.size(); i>0; i--){
            int idParticipant = particpants.get(i-1).getId();
            Map<Integer, Set<Integer>> adversairePropre = adversaireHistorique.get(idParticipant);
            boolean etaitBye = adversairePropre.containsKey(idParticipant);
            if(!etaitBye){
                PlayerSwissDTO playerDernier = particpants.remove(i-1);
                boolean appariementSucces = chercherAdversaire(num_manche, particpants, adversaireHistorique, matchsTemporaires, num_table, mancheInt, niveau);
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
        throw new AppariementException("probleme d'appariement");
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
                                       Map<Integer, Map<Integer,Set<Integer>>> adversaireHistorique,
                                       ArrayList<MatchSwiss> matchsTemporaires,
                                       int numTable, int mancheInt, int niveau) {
        if(particpants.isEmpty()){
            return true;
        }
        PlayerSwissDTO playerWhite = particpants.removeFirst();
        for(int i=0; i<particpants.size();i++){
            PlayerSwissDTO playerBlackPotentiel = particpants.get(i);
           if(peuventSeRencontrer(playerWhite.getId(),playerBlackPotentiel.getId(),adversaireHistorique,niveau,mancheInt)){
                PlayerSwissDTO playerBlack = particpants.remove(i);
                String num_match = num_manche + "->" + numTable;
                MatchSwiss m = new MatchSwiss();
                m.buildMatch(num_manche,playerWhite.getId(),playerWhite.getNom(),playerBlack.getId(),playerBlack.getNom(),num_match,numTable);
                matchsTemporaires.add(m);
               /**
                * dessiner un groupe retirer les 2 premier pour buildMatch, si je reprend la fonction avec les paramettre actuel je fait une reccurssion de methode jusqu'a isEmpty.
                */
               if(chercherAdversaire(num_manche,particpants,adversaireHistorique,matchsTemporaires,numTable+1, mancheInt,niveau)){
                    return true;
                }
                //si le true ne viens pas, alors on annule le match enregistré et remet le player black dans la liste.
                matchsTemporaires.removeLast();
                particpants.add(i,playerBlack);
            }
        }
        particpants.addFirst(playerWhite);
        return false;
    }
    private boolean peuventSeRencontrer(int idPlayerW, int idPlayerB, Map<Integer,Map<Integer,Set<Integer>>> historique,int niveau, int mancheInt){
        Map<Integer,Set<Integer>> adversaires = historique.get(idPlayerW);
        if(adversaires==null || !adversaires.containsKey(idPlayerB)){
            return true; // il ne se sont jamais rencontrer, match permis.
        }
        if(niveau==0){
            return false; // interdiction de revance, ils se sont rencontrer
        }
        if(niveau==1){
            return !adversaires.get(idPlayerB).contains(mancheInt-1); // permission de revanche si ils ne se sont pas rencontres dans la manche precedeante.
        }
        return true;
    }

    public TreeSet<MatchSwiss> getMatchByManche(String num_manche){
        TreeSet<MatchSwiss> m = matchSwissRepestory.findMatchBynumManche(num_manche);
        return m;
    }
    public TreeSet<MatchSwissDTO> chargerHistorique(int i) {
        return matchSwissRepestory.chargerHistorique(i);
    }
}