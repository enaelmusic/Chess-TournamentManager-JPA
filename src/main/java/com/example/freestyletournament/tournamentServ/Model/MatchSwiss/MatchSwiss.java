package com.example.freestyletournament.tournamentServ.Model.MatchSwiss;

import jakarta.persistence.*;

@Entity
@Table(name="MATCHSWISS")
public class MatchSwiss implements Comparable<MatchSwiss> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idPlayerW, idPlayerB, tableNum;
    @Column(name = "status", columnDefinition = "TINYINT")
    private int status;
    private String num_manche;
    private String nom_white;
    private String nom_black ;
    private String num_match;

    public void setNom_black(String nom_black) {
        this.nom_black = nom_black;
    }
    public void setNom_white(String nom_white) {
        this.nom_white = nom_white;
    }
    public String getNum_manche() {
        return num_manche;
    }

    public void setNum_manche(String num_manche) {
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

    public String getNum_match() {
        return num_match;
    }

    public void setNum_match(String num_match) {
        this.num_match = num_match;
    }

    @Override
    public int compareTo(MatchSwiss o) {
        return this.num_match.compareTo(o.getNum_match());
    }

    public void buildMatch(String num_manche, int idPlayerW, String nom_white, int idPlayerB, String nom_black, String num_match, int numTable) {
        this.num_manche=num_manche;
        this.idPlayerW=idPlayerW;
        this.nom_white=nom_white;
        this.idPlayerB=idPlayerB;
        this.nom_black=nom_black;
        this.tableNum=numTable;
        setNum_match(num_match);
    }
}
