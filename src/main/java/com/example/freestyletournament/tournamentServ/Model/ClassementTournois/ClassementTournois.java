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


    @Override
    public int compareTo(ClassementTournois o) {
        return 0;
    }
}
