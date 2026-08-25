package com.example.freestyletournament.tournamentServ.Model.PlayerTournois;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="PLAYER_TOURNOIS")
public class PlayerTournois {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idPlayer;
    private int idTournois;
    @Column(name = "status", columnDefinition = "TINYINT")
    private int status;


    public int getId() {
        return this.id;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdTournois() {
        return idTournois;
    }

    public void setIdTournois(int idTournois) {
        this.idTournois = idTournois;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerTournois that = (PlayerTournois) o;
        return id == that.id && idPlayer == that.idPlayer && idTournois == that.idTournois;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idPlayer, idTournois);
    }
}
