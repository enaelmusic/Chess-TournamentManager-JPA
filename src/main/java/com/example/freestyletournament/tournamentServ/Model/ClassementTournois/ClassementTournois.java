package com.example.freestyletournament.tournamentServ.Model.ClassementTournois;

import jakarta.persistence.*;

@Entity
@Table(name="CLASSEMENT_TOURNOIS")
public class ClassementTournois implements Comparable<ClassementTournois> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idTournois;
    private int idPlayer;
    private int round_gagner;
    private int round_perdu;
    private int round_null;
    private int point;

    public int getId() {
        return id;
    }

    public int getIdTournois() {
        return idTournois;
    }

    public void setIdTournois(int idTournois) {
        this.idTournois = idTournois;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getRound_gagner() {
        return round_gagner;
    }

    public void setRound_gagner(int round_gagner) {
        this.round_gagner = round_gagner;
    }

    public int getRound_perdu() {
        return round_perdu;
    }

    public void setRound_perdu(int round_perdu) {
        this.round_perdu = round_perdu;
    }

    public int getRound_null() {
        return round_null;
    }

    public void setRound_null(int round_null) {
        this.round_null = round_null;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public int compareTo(ClassementTournois o) {
        return 0;
    }
}
