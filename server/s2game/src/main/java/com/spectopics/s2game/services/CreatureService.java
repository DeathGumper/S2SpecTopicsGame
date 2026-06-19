package com.spectopics.s2game.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.spectopics.s2game.models.Creature;

public class CreatureService {
    /* 
        All metadata for the creatures are parsed and then stored here. Use GetNew to get a new instance of the creature, 
        and GetRandomCreature to get a random one. 
        GetRandomCreatureOptions gets a list of random creatures, used for the creature selection screen.
    */
    public static Random rand = new Random();
    public static HashMap<String, Creature> creatures = new HashMap<>();

    // Random creature, for refreshing.
    public static Creature GetRandomCreature() {
        List<String> names = new ArrayList<>(creatures.keySet());
        String name = names.get(rand.nextInt(names.size()));
        Creature creature = GetNew(name);
        return creature;
    }

    // For refreshing, get an array of random creatures.
    public static Creature[] GetRandomCreatureOptions(int amount) {
        Creature[] creatures = new Creature[amount];
        for (int i = 0; i < amount; i++) {

            // Kinda a weird way to do it but it works, get a random creature and check if its already in the array, 
            // if it is get a new one.
            Creature newCreature = GetRandomCreature();
            for (Creature creat: creatures) {
                if (creat == null) continue;
                if (creat.getName().equals(newCreature.getName())) {
                    i--;
                    continue;
                }
            }
            creatures[i] = newCreature;
            System.out.println(creatures[i]);
        }
        return creatures;
    }

    // Creature declaration stuff
    // Called by the metadata service
    public static void AddNew(Creature creature) {
        creatures.put(creature.getName().replaceAll(" ", "").toLowerCase(), creature);
    }
    
    // Getter by name, maybe id would be better but for now name is fine.
    public static Creature GetNew(String name) {
        return creatures.get(name).copy();
    }
}
