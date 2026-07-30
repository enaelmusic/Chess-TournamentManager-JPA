package com.example.freestyletournament.tournamentServ.Model.PlayerSwiss;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="PLAYER")
public class PlayerSwiss implements Comparable<PlayerSwiss> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private int num_fide;

    private String num_joueur;
    private int ratingBullet;
    private int ratingBlitz;
    private int ratingRapid;
    private int ratingStd;

    public void setNum_joueur(String num_joueur) {
        this.num_joueur = num_joueur;
    }
    public String getNum_joueur() {
        return this.num_joueur;
    }

    public int getRatingStd() {
        return ratingStd;
    }

    public void setRatingStd(int ratingStd) {
        this.ratingStd = ratingStd;
    }

    public int getRatingRapid() {
        return ratingRapid;
    }

    public void setRatingRapid(int ratingRapid) {
        this.ratingRapid = ratingRapid;
    }

    public int getRatingBlitz() {
        return ratingBlitz;
    }

    public void setRatingBlitz(int ratingBlitz) {
        this.ratingBlitz = ratingBlitz;
    }

    public int getRatingBullet() {
        return ratingBullet;
    }

    public void setRatingBullet(int ratingBullet) {
        this.ratingBullet = ratingBullet;
    }

    public int getNumFide() {
        return num_fide;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSwiss that = (PlayerSwiss) o;
        return id == that.id && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }

    @Override
    public int compareTo(PlayerSwiss o) {
        if(o.getNom().equals(this.nom)){
            return o.getId()-this.getId();
        }
        else{
            return this.getNom().compareTo(o.getNom());
        }
    }
}
