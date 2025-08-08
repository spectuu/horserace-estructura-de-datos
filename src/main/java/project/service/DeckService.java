package project.service;

import project.model.Card;
import project.util.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeckService {

    private final List<Card> deck = new ArrayList<>();
    private final Random rnd;

    public DeckService(long seed) {
        rnd = new Random(seed);
        initDeck();
    }

    public DeckService() {
        this(System.currentTimeMillis());
    }

    private void initDeck() {
        deck.clear();
        for (Suit s : Suit.values()) {
            for (int v = 1; v <= 12; v++) {
                deck.add(new Card(s, v));
            }
        }
        Collections.shuffle(deck, rnd);
    }

    public Card draw() {
        if (deck.isEmpty()) initDeck();
        return deck.remove(deck.size() - 1);
    }

}
