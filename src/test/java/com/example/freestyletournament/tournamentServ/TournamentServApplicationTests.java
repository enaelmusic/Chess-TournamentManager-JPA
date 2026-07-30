package com.example.freestyletournament.tournamentServ;

import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwissDAO;
import com.example.freestyletournament.tournamentServ.Model.PlayerTournois.PlayerTournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.PlayerTournois.PlayerTournoisModel;
import com.example.freestyletournament.tournamentServ.Model.Tournois.Tournois;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisDAO;
import com.example.freestyletournament.tournamentServ.Model.Tournois.TournoisModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.TreeSet;

@SpringBootTest
class TournamentServApplicationTests {
	@Autowired
	private TournoisDAO daoTournois ;
	@Autowired
	private PlayerSwissDAO daoPlayer;
	@Autowired
	private PlayerTournoisDAO ptDAO;

	@Test
	void contextLoads() {
        Tournois t1 = new Tournois();
		PlayerSwiss p = new PlayerSwiss();
		PlayerSwiss p1 = new PlayerSwiss();
		PlayerSwiss p2 = new PlayerSwiss();
		PlayerSwiss p3 = new PlayerSwiss();
		PlayerSwiss p4 = new PlayerSwiss();
		t1.setName("tournois du jour 5");
		t1.setCadence("3 | 2");
		t1.setStatusTournois(2);
		daoTournois.sauverTournois(t1);
		enregistreJoueur("Pierre",p);
		enregistrerPlayertournois(p,t1);
		enregistreJoueur("Jeanne",p1);
		enregistrerPlayertournois(p1,t1);
		enregistreJoueur("Parfait",p2);
		enregistrerPlayertournois(p2,t1);
		enregistreJoueur("Blanca",p3);
		enregistrerPlayertournois(p3,t1);
		enregistreJoueur("Moloud",p4);
		enregistrerPlayertournois(p4,t1);
	}


	private void enregistreJoueur(String nom, PlayerSwiss p){
		p.setNom(nom);
		daoPlayer.sauverPlayer(p);
	}


	private void enregistrerPlayertournois(PlayerSwiss p , Tournois t1){
		int pid= p.getId();
		int tid= t1.getId();
		ptDAO.savePlayerTournois(tid, pid);
	}

	@Test
	void modifierJoueur(){
		PlayerSwiss p = daoPlayer.getPlayer("P-2026-07-29-1001");
		p.setRatingBlitz(1562);
		daoPlayer.sauverPlayer(p);
	}
	@Test
	void returSetPlayer(){

		TreeSet<PlayerSwiss> setPlayer = ptDAO.getPlayerByIdTournois(26);
		for(PlayerSwiss p: setPlayer){
			System.out.print(p.toString());
		}
	}

}
