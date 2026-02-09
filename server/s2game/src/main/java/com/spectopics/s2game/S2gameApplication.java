package com.spectopics.s2game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spectopics.s2game.services.MetaDataService;

@SpringBootApplication
public class S2gameApplication {

	public static void main(String[] args) {
		SpringApplication.run(S2gameApplication.class, args);
		MetaDataService.loadCreatureData();
	}

}
