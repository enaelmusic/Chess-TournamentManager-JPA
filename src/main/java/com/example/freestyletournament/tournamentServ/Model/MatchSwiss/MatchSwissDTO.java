package com.example.freestyletournament.tournamentServ.Model.MatchSwiss;

public class MatchSwissDTO implements Comparable<MatchSwissDTO>{
    private int id, idPlayerW, idPlayerB, int_manche;

    public MatchSwissDTO(int id,int int_manche, int idPlayerW, int idPlayerB){
        this.id=id;
        this.int_manche=int_manche;
        this.idPlayerB=idPlayerB;
        this.idPlayerW=idPlayerW;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInt_manche() {
        return int_manche;
    }

    public void setInt_manche(int int_manche) {
        this.int_manche = int_manche;
    }

    public int getIdPlayerW() {
        return idPlayerW;
    }
    public void setIdPlayerW(int idPlayerW) {
        this.idPlayerW = idPlayerW;
    }

    public int getIdPlayerB() {
        return idPlayerB;
    }

    public void setIdPlayerB(int idPlayerB) {
        this.idPlayerB = idPlayerB;
    }

    @Override
    public int compareTo(MatchSwissDTO o) {
            return this.id-o.getId();
    }
}
