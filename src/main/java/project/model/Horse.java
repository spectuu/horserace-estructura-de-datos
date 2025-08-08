package project.model;

import project.util.Suit;

import java.io.Serializable;
import java.util.UUID;

public class Horse implements Serializable {

    private final UUID id;
    private final Suit suit;
    private int position = 0;

    public Horse(Suit suit) {
        this.id = UUID.randomUUID();
        this.suit = suit;
    }

    public Suit getSuit() { return suit; }

    public int getPosition() { return position; }

    public void advance() { position++; }

    public void retreat() { if (position > 0) position--; }

    public String getName() { return suit.name(); }

    @Override
    public String toString() {
        return getName() + "(pos=" + position + ")";
    }
}
