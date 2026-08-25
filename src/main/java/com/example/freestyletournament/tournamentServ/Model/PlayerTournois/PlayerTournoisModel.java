package com.example.freestyletournament.tournamentServ.Model.PlayerTournois;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.Set;
import java.util.TreeSet;

@Repository
public interface PlayerTournoisModel extends CrudRepository<PlayerTournois, Integer> {
    @NativeQuery("SELECT * FROM PLAYER_TOURNOIS where idTournois=?1")
    Set<PlayerTournois> findAllByIdTournois(int idTournois);
    @NativeQuery("SELECT * FROM PLAYER_TOURNOIS where idPlayer=?1")
    PlayerTournois findPlayerById(int idPlayer);
}
