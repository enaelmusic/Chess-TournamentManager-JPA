package com.example.freestyletournament.tournamentServ.Model.ModelService;

public enum TournoisEnum {
    ENATTENT(0),
    ENREGISTRER(1),
    ENCOUR(2),
    FINI(3);

    private int tyni;
    private TournoisEnum(int i){
        this.tyni=i;
    }

    public int getTyni() {
        return tyni;
    }
}
