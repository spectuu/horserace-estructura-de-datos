package project.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.Card;
import project.model.Horse;
import project.util.Suit;

import java.io.IOException;
import java.util.*;

public class RaceService {

    private static final Logger LOG = LogManager.getLogger(RaceService.class);

    private final Map<Suit, Horse> horses = new EnumMap<>(Suit.class);
    private final Deque<Card> stagesStack = new ArrayDeque<>();
    private final Queue<Horse> finishQueue = new ArrayDeque<>();

    private final DeckService deck;
    private final PersistenceService persistence;
    private final int totalStages;
    private int currentStage = 0;

    public RaceService(DeckService deck, PersistenceService persistence, int totalStages) {
        this.deck = deck;
        this.persistence = persistence;
        this.totalStages = totalStages;
        initHorses();
        initStages();
    }

    private void initHorses() {
        for (Suit s : Suit.values()) horses.put(s, new Horse(s));
    }

    private void initStages() {
        for (int i = 0; i < 8; i++) {
            stagesStack.push(deck.draw());
        }
    }

    private static final Map<Suit, String> SUIT_SYMBOLS = Map.of(
            Suit.GOLD, "$",
            Suit.STICK, "/",
            Suit.CUP, "u",
            Suit.SWORD, "|"
    );

    private void renderRace() {
        StringBuilder sb = new StringBuilder();
        sb.append("\033[H\033[2J");
        sb.append("Horse Race \n\n");

        for (Horse h : horses.values()) {
            sb.append(String.format("%-8s |", h.getName()));
            for (int i = 0; i <= totalStages; i++) {
                if (i == h.getPosition()) {
                    sb.append(" i ");
                } else {
                    sb.append(" ---");
                }
            }
            sb.append("\n");
        }

        sb.append("\nCartas por etapa:\n");
        int etapa = 1;
        for (Card c : stagesStack) {
            sb.append("Etapa ").append(etapa++).append(": ").append("?").append("\n");
        }

        System.out.print(sb.toString());
    }

    public void runRace() {

        LOG.info("Start the race");
        int turn = 0;

        while (finishQueue.size() < horses.size()) {

            turn++;
            Card c = deck.draw();
            Horse h = horses.get(c.getSuit());

            if (finishQueue.contains(h)) continue;

            h.advance();

            if (allHorsesPassedStage(currentStage + 1) && !stagesStack.isEmpty()) {
                Card hidden = stagesStack.pop();
                Horse affected = horses.get(hidden.getSuit());
                if (affected != null) affected.retreat();
                currentStage++;
            }

            saveState();
            renderRace();
            try { Thread.sleep(500); } catch (InterruptedException e) { }

            if (h.getPosition() >= totalStages) finishQueue.add(h);

        }
    }

    private boolean allHorsesPassedStage(int stage) {
        return horses.values().stream().allMatch(h -> h.getPosition() > stage);
    }

    private void saveState() {
        try {
            persistence.dumpReadable("lastRace.txt", new ArrayList<>(horses.values()));
            persistence.saveBinary("lastRace.ser", new ArrayList<>(horses.values()));
        } catch (IOException e) {
            LOG.error("Error guardando estado", e);
        }
    }

    public List<Horse> getFinishOrder() {
        return new ArrayList<>(finishQueue);
    }

}
