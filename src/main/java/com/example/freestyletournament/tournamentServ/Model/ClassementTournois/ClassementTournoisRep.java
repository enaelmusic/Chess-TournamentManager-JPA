package com.example.freestyletournament.tournamentServ.Model.ClassementTournois;

import com.example.freestyletournament.tournamentServ.Model.PlayerSwiss.PlayerSwiss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.TreeSet;

@Repository
public interface ClassementTournoisRep extends JpaRepository<ClassementTournois, Integer> {
    @NativeQuery("SELECT * FROM CLASSEMENT_TOURNOIS WHERE idTournois= ?1 AND idPlayer = ?2 LIMIT 1 ")
    ClassementTournois findClassementByIdTournoisAndIdPlayer(int idTournois, int idPlayer);

    @NativeQuery("SELECT * FROM PLAYER PT JOIN CLASSEMENT_TOURNOIS CT ON PT.id=CT.idPlayer WHERE CT.round_gagner= ( SELECT MAX(round_gagner) FROM CLASSEMENT_TOURNOIS ) AND NOT CT.idPlayer=?1 ORDER BY CT.round_gagner AND CT.round_perdu AND CT.round_null DESC LIMIT 1")
    PlayerSwiss findAdversaireQuery(int idPlayer);
    @NativeQuery("SELECT * FROM PLAYER PT JOIN CLASSEMENT_TOURNOIS CT ON PT.id=CT.idPlayer WHERE CT.round_gagner= ( SELECT MAX(round_gagner) FROM CLASSEMENT_TOURNOIS ) ORDER BY CT.round_gagner AND CT.round_perdu AND CT.round_null DESC LIMIT 1;")
    PlayerSwiss findMeilleurJoueur();


    //utilisable
    @NativeQuery("SELECT PT.* FROM PLAYER PT JOIN CLASSEMENT_TOURNOIS CT ON PT.id=CT.idPlayer WHERE idTournois=?1 ORDER BY CT.point DESC , CT.round_gagner , CT.round_perdu , CT.round_null DESC")
    ArrayList<PlayerSwiss> getPlayersClassee(int idTournois);
    @Query("SELECT new com.example.freestyletournament.tournamentServ.Model.ClassementTournois.ClassementDTO(CT.idPlayer, P.nom, CT.point) FROM ClassementTournois CT JOIN PlayerSwiss P ON P.id=CT.idPlayer WHERE CT.idTournois=:idTournois ORDER BY CT.point DESC , CT.round_gagner , CT.round_perdu , CT.round_null DESC")
    ArrayList<ClassementDTO> getClassementTournois(String idTournois);
}
