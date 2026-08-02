package com.example.freestyletournament.tournamentServ.Model.ModelService;

public enum MancheEnum {
    ENATTENT(0),
    ENCOURS(1),
    FINI(2);

    private int tyni;
    private MancheEnum(int i){
        this.tyni=i;
    }

    public int getTyni() {
        return tyni;
    }
}
