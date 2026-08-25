package com.example.freestyletournament.tournamentServ.Model.PlayerSwiss;

public class PlayerSwissDTO implements Comparable<PlayerSwissDTO> {
    private int id;
    private String nom;

    public PlayerSwissDTO(int id, String nom) {
        this.id = id;
        this.nom = nom;
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
