package com.example.freestyletournament.tournamentServ.Model.Tournois;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;

@Repository
public interface TournoisModel extends CrudRepository<Tournois, Integer> {
    @NativeQuery("SELECT id FROM tournois WHERE num_tournois= ?1 LIMIT 1")
    Tournois findIdTournoisByNumTournois(String num_tournois);
    @NativeQuery("SELECT * FROM tournois WHERE id= ?1")
    Tournois findIdTournoisByiDtournois(int id);
}
