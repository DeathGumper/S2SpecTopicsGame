package com.spectopics.s2game.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.spectopics.s2game.models.Creature;

public class CreatureService {
    public static Random rand = new Random();
    public static HashMap<String, Creature> creatures = new HashMap<>();

    public static Creature GetRandomCreature() {
        List<String> names = new ArrayList<>(creatures.keySet());
        String name = names.get(rand.nextInt(names.size()));
        Creature creature = GetNew(name);
        return creature;
    }


    // Creature declaration stuff
    public static void AddNew(Creature creature) {
        creatures.put(creature.getName().replaceAll(" ", "").toLowerCase(), creature);
    }

    public static Creature GetNew(String name) {
        return creatures.get(name).copy();
    }
}
