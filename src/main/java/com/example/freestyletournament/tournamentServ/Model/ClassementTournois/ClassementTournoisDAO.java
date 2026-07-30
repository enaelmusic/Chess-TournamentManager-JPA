package com.example.freestyletournament.tournamentServ.Model.ClassementTournois;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassementTournoisDAO {
    @Autowired
    private ClassementTournoisRep classement;
}
