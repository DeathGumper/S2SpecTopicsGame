package com.spectopics.s2game.services;

import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tools.jackson.databind.ObjectMapper;
import com.spectopics.s2game.models.Creature;

public class MetaDataService {
    public static void loadCreatureData() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:creatures/*.json");

            ObjectMapper mapper = new ObjectMapper();
            for (Resource resource : resources) {
                try {
                    Creature creature = mapper.readValue(resource.getInputStream(), Creature.class);
                    // Abilities from the json doesnt seem to be getting to the creature class
                    // So i want to maually set the abilities here
                    // I will read the json as a map and then set the abilities, which i will turn into a map, to the creature
                    System.out.println(mapper.convertValue(creature, Map.class).get("abilities"));
                    //creature.setAbilities();
                    CreatureService.AddNew(creature);
                    System.out.println("Loaded creature: " + creature.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Loaded " + resources.length + " creatures");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadCreatureData();
    }
}
