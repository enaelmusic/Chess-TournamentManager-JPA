package com.example.freestyletournament.tournamentServ.Model.Tournois;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;

@Repository
public interface TournoisModel extends CrudRepository<Tournois, Integer> {
    @NativeQuery("SELECT * FROM tournois WHERE num_tournois= ?1 LIMIT 1")
    Tournois findIdTournoisByNumTournois(String num_tournois);
    @NativeQuery("SELECT * FROM tournois WHERE id= ?1")
    Tournois findIdTournoisByiDtournois(int id);

    @NativeQuery("SELECT T.* FROM tournois T JOIN TEST_MANCHESWISS TMS ON TMS.num_tournois=T.num_tournois WHERE TMS.num_manche=?1 LIMIT 1")
    Tournois findTournoisByNumManche(String num_manche);
}
