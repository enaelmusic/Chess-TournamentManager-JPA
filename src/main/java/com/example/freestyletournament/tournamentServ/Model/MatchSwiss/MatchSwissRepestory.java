package com.example.freestyletournament.tournamentServ.Model.MatchSwiss;

import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchSwissRepestory extends CrudRepository<MatchSwiss, Integer> {
    @NativeQuery("SELECT * FROM MATCHSWISS WHERE num_tournois=?1 LIMIT 1")
    List<MatchSwiss> findIdMatchByTournois(String num_tournois);
}
