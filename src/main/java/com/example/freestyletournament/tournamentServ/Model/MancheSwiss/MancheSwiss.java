package com.example.freestyletournament.tournamentServ.Model.MancheSwiss;

import jakarta.persistence.*;

@Entity
@Table(name="TEST_MANCHESWISS")
public class MancheSwiss implements Comparable<MancheSwiss> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String num_tournois;
    private String num_manche;
    @Column(name = "status", columnDefinition = "TINYINT")
    private int status;

    public int getId(){
        return id;
    }
    public String getNum_manche() {
        return num_manche;
    }

    public void setNum_manche(String num_manche) {
        this.num_manche = num_manche;
    }

    public String getNum_tournois() {
        return num_tournois;
    }

    public void setNum_tournois(String num_tournois) {
        this.num_tournois = num_tournois;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int compareTo(MancheSwiss o) {
        return this.id-o.getId();
    }
}
