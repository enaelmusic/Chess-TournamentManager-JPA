package com.example.freestyletournament.tournamentServ.Model.Tournois;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="tournois")
public class Tournois {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String cadence;
    private int status_tournois;
    private int duree_min;
    private int nbr_manche;
    private String num_tournois;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNbr_manche() {
        return nbr_manche;
    }
    public void setNbr_manche(int nbr_manche) {
        this.nbr_manche = nbr_manche;
    }
    public String getCadence() {
        return cadence;
    }
    public int getStatusTournois() {
        return status_tournois;
    }
    public void setStatusTournois(int statusTournois){
        this.status_tournois=statusTournois;
    }
    public int getDuree_min() {
        return duree_min;
    }
    public void setDuree_min(int duree_min) {
        this.duree_min = duree_min;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCadence(String cadence) {
        this.cadence = cadence;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tournois tournois = (Tournois) o;
        return id == tournois.id && Objects.equals(name, tournois.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public void setNum_tournois(String numUnique) {
        this.num_tournois=numUnique;
    }

    public String getNum_tournois(){
        return this.num_tournois;
    }
}
