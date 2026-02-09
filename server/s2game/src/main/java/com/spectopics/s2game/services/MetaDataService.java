package com.spectopics.s2game.services;

import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import tools.jackson.databind.ObjectMapper;
import com.spectopics.s2game.models.Creature;

public class MetaDataService {
    public static void loadCreatureData() {
        try {
            // Get the resources/creatures directory relative to the classpath
            Path creaturesPath = Paths.get(ClassLoader.getSystemResource("creatures").toURI());
            
            // Read all JSON files from the creatures folder
            try (Stream<Path> paths = Files.list(creaturesPath)) {
                // Get all the creatures from JSON files
                List<Creature> creatures = paths
                    .filter(path -> path.toString().endsWith(".json"))
                    .flatMap((Path path) -> {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            Creature creature = mapper.readValue(Files.readString(path), Creature.class);
                            return Stream.of(creature);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return Stream.empty();
                        }
                    })
                    .collect(Collectors.toList());
                
                // Debug: Print loaded creatures
                System.out.println("Loaded " + creatures.size() + " creatures");
                for (Creature creature : creatures) {
                    System.out.println("Creature: " + creature.getName() + ", Stats: " + creature.getStats());
                }

                // Add creatures to the Creature's static collection
                for (Creature creature : creatures) {
                    Creature.AddNew(creature);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadCreatureData();
    }
}