package com.example.freestyletournament.tournamentServ.Model.PlayerSwiss;

public class PlayerSwissDTO implements Comparable<PlayerSwissDTO> {
    private int id;
    private String nom;
    private int statusTournois;

    public PlayerSwissDTO(int id, String nom, int statusTournois) {
        this.id = id;
        this.nom = nom;
        this.statusTournois=statusTournois;
    }

    public int getStatusTournois() {
        return statusTournois;
    }

    public void setStatusTournois(int statusTournois) {
        this.statusTournois = statusTournois;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int compareTo(PlayerSwissDTO o) {
        if(o.getNom().equals(this.nom)){
            return o.getId()-this.getId();
        }
        else{
            return this.getNom().compareTo(o.getNom());
        }
    }
}
