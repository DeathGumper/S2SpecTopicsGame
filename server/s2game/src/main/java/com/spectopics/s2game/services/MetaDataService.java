package com.spectopics.s2game.services;

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
