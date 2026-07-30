package com.example.freestyletournament.tournamentServ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.freestyletournament.tournamentServ" )
public class TournamentServApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentServApplication.class, args);
	}

}
