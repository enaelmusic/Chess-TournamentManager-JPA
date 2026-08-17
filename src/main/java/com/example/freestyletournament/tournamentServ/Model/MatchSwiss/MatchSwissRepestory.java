package com.example.freestyletournament.tournamentServ.Model.MatchSwiss;

import com.example.freestyletournament.tournamentServ.Model.MancheSwiss.MancheSwiss;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Repository
public interface MatchSwissRepestory extends CrudRepository<MatchSwiss, Integer> {
    @NativeQuery("SELECT * FROM MATCHSWISS WHERE num_tournois=?1 LIMIT 1")
    List<MatchSwiss> findIdMatchByTournois(String num_tournois);

    @NativeQuery("SELECT * FROM MATCHSWISS WHERE num_manche=?1")
    TreeSet<MatchSwiss> findMatchBynumManche(String num_manche);
}
