package com.example.freestyletournament.tournamentServ.Model.RoundSwiss;

import jakarta.persistence.*;

@Entity
@Table(name="ROUNDSWISS")
public class RoundSwiss implements Comparable<RoundSwiss> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idPlayerW, idPlayerB, tableNum;
    @Column(name = "status", columnDefinition = "TINYINT")
    private int status;
    private int num_manche;
    private String num_tournois;
    private String nom_white;
    private String nom_black ;

    public void setNom_black(String nom_black) {
        this.nom_black = nom_black;
    }
    public void setNom_white(String nom_white) {
        this.nom_white = nom_white;
    }
    public int getNum_manche() {
        return num_manche;
    }

    public void setNum_manche(int num_manche) {
        this.num_manche = num_manche;
    }

    public void setIdPlayerB(int idPlayerB) {
        this.idPlayerB = idPlayerB;
    }

    public void setIdPlayerW(int idPlayerW) {
        this.idPlayerW = idPlayerW;
    }

    public String getNom_black() {
        return nom_black;
    }

    public String getNom_white() {
        return nom_white;
    }

    public String getNum_tournois() {
        return num_tournois;
    }

    public void setNum_tournois(String num_tournois) {
        this.num_tournois = num_tournois;
    }

    public int getIdPlayerW() {
        return idPlayerW;
    }

    public int getIdPlayerB() {
        return idPlayerB;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(RoundSwiss o) {
        return this.id - o.getId();
    }

    public void buildRS(String numTournois, int idPlayerW, String nom_white, int idPlayerB, String nom_black) {
        this.num_tournois=numTournois;
        this.idPlayerW=idPlayerW;
        this.nom_white=nom_white;
        this.idPlayerB=idPlayerB;
        this.nom_black=nom_black;
    }
}
