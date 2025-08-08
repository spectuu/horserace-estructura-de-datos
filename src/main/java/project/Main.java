package project;

import project.service.DeckService;
import project.service.PersistenceService;
import project.service.RaceService;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        DeckService deck = new DeckService();
        PersistenceService p = new PersistenceService(Path.of("data"));
        RaceService race = new RaceService(deck, p, 10);
        race.runRace();
    }
}