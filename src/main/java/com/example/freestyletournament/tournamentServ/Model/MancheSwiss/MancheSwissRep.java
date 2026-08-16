package com.example.freestyletournament.tournamentServ.Model.MancheSwiss;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.TreeSet;

@Repository
public interface MancheSwissRep extends CrudRepository<MancheSwiss,Integer> {
    @NativeQuery("SELECT * FROM TEST_MANCHESWISS WHERE num_manche=?1 LIMIT 1")
    MancheSwiss findIdMancheByManche(String num_manche);
    @NativeQuery("SELECT * FROM TEST_MANCHESWISS WHERE num_tournois=?1")
    ArrayList<MancheSwiss> findAllMancheByTournois(String num_tournois);
    @NativeQuery("SELECT * FROM TEST_MANCHESWISS WHERE id=?1 LIMIT 1")
    ArrayList<MancheSwiss> findMancheById(int id);
}
