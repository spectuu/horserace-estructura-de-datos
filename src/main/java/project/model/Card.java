package project.model;

import project.util.Suit;

import java.io.Serializable;
import java.util.UUID;

public class Card implements Serializable {

    private final UUID id;
    private final Suit suit;
    private final int value;

    public Card(Suit suit, int value) {
        this.id = UUID.randomUUID();
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() { return suit; }
    public int getValue() { return value; }


    @Override
    public String toString() {
        return suit + "-" + value;
    }

}
