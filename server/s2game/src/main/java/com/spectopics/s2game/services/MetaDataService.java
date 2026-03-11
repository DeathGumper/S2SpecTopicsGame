package com.spectopics.s2game.services;

import java.io.InputStream;

import tools.jackson.databind.ObjectMapper;
import com.spectopics.s2game.models.Creature;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class MetaDataService {

    public static void loadCreatureData() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            PathMatchingResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();

            Resource[] resources =
                    resolver.getResources("classpath*:creatures/*.json");

            int count = 0;

            for (Resource resource : resources) {

                try (InputStream stream = resource.getInputStream()) {

                    Creature creature =
                            mapper.readValue(stream, Creature.class);

                    Creature.AddNew(creature);

                    count++;

                    System.out.println("Loaded creature: " + creature.getName());

                } catch (Exception e) {
                    System.err.println("Failed loading: " + resource.getFilename());
                    e.printStackTrace();
                }
            }

            System.out.println("Loaded " + count + " creatures total");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}