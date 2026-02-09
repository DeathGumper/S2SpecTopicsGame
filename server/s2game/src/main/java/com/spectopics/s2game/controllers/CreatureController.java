package com.spectopics.s2game.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.spectopics.s2game.models.Creature;
import com.spectopics.s2game.models.LobbyState;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

@RestController
@RequestMapping("/creature")
public class CreatureController {
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    @GetMapping("/spawn/{creatureType}/{playerName}")
    public String spawnCreature(@PathVariable String creatureType, @PathVariable String playerName) {
        Creature creature = new Creature().GetNew(creatureType);
        LobbyState.GetPlayer(playerName).AddCreature(creature);
        String json = ow.writeValueAsString(creature);
        return json;
    }
}
