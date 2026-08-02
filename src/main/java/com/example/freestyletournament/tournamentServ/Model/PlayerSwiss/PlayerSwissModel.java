package com.example.freestyletournament.tournamentServ.Model.PlayerSwiss;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerSwissModel extends CrudRepository<PlayerSwiss, Integer> {
    @NativeQuery("SELECT * FROM PLAYER WHERE num_joueur= ?1 LIMIT 1")
    PlayerSwiss findByNum_joueur(String num_joueur);
    @NativeQuery("SELECT * FROM PLAYER WHERE id= ?1 LIMIT 1")
    PlayerSwiss findById_joueurJ(int id);
}
