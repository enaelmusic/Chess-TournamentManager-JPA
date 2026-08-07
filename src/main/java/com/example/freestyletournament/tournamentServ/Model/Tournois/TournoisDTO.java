package com.example.freestyletournament.tournamentServ.Model.Tournois;

public class TournoisDTO {
    private String num_tournois, name;
    private int nbr_manches, status;

    public TournoisDTO(int nbr_manches, String name, String num_tournois,int status) {
        this.nbr_manches = nbr_manches;
        this.name = name;
        this.num_tournois = num_tournois;
        this.status=status;
    }

    public String getNum_tournois() {
        return num_tournois;
    }

    public void setNum_tournois(String num_tournois) {
        this.num_tournois = num_tournois;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbr_manches() {
        return nbr_manches;
    }

    public void setNbr_manches(int nbr_manches) {
        this.nbr_manches = nbr_manches;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
