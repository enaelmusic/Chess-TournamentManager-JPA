package com.example.freestyletournament.tournamentServ.Model.MatchSwiss;

import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface MatchSwissRepestory extends JpaRepository<MatchSwiss, Integer> {
    @Query("SELECT new com.example.freestyletournament.tournamentServ.Model.MatchSwiss.MatchSwissDTO(MS.id, TMS.int_manche , MS.idPlayerW, MS.idPlayerB) FROM MatchSwiss MS JOIN MancheSwiss TMS ON MS.num_manche=TMS.num_manche JOIN Tournois T ON T.num_tournois=TMS.num_tournois WHERE T.id=?1")
    TreeSet<MatchSwissDTO> chargerHistorique(int idTournois);
    @NativeQuery("SELECT COUNT(*) FROM MatchSwiss MS JOIN TEST_MANCHESWISS TMS ON MS.num_manche=TMS.num_manche JOIN Tournois T ON T.num_tournois=TMS.num_tournois WHERE T.id=?1 AND MS.idPlayerW=?2 AND MS.idPlayerB=?3 OR T.id=?1 AND MS.idPlayerW=?3 AND MS.idPlayerB=?2")
    int nbreRencontreEntreParticipant(int idTouns,int idPlayerW, int idPlayerB);
    @NativeQuery("SELECT * FROM MATCHSWISS WHERE num_tournois=?1 LIMIT 1")
    List<MatchSwiss> findIdMatchByTournois(String num_tournois);

    @NativeQuery("SELECT * FROM MATCHSWISS WHERE num_manche=?1")
    TreeSet<MatchSwiss> findMatchBynumManche(String num_manche);


}
