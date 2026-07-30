package com.example.freestyletournament.tournamentServ.Model.ClassementTournois;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassementTournoisRep extends CrudRepository<ClassementTournois, Integer> {
}
