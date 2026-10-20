package com.example.freestyletournament.tournamentServ.Model.ModelService;

public enum MatchStatus {
    ENATTENT(0),
    WHITE(1),
    BLACK(2),
    DRAW(3),
    BYE(4);

    private int tyni;
    private MatchStatus(int i){
        this.tyni=i;
    }

    public int getTyni() {
        return tyni;
    }
}
