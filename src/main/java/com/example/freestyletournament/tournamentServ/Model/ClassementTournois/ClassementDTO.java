package com.example.freestyletournament.tournamentServ.Model.ClassementTournois;

public class ClassementDTO {
    private int idPlayer;
    private String nom;
    private int point;
    private int status;
    public ClassementDTO(int idPlayer, String nom, int point, int status){
        this.idPlayer=idPlayer;
        this.nom=nom;
        this.point=point;
        this.status=status;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public String getNom() {
        return nom;
    }

    public int getPoint() {
        return point;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
