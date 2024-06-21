/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;

/**
 * A class that models each Player in the game. Players have an identifier, which should be unique.
 *
 * @author Tech Titans
 * @author gursegur June 2024
 */


import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private final String name;
    private final ArrayList<Card> hand;
    private final HashMap<String, Integer> bookCounts;
    private int score;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.bookCounts = new HashMap<>();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addBook(String rank) {
        bookCounts.put(rank, bookCounts.getOrDefault(rank, 0) + 1);
        score += 1; // Increment score for each book
    }

    public int getBookCount(String rank) {
        return bookCounts.getOrDefault(rank, 0);
    }

    public boolean hasCardWithRank(String rank) {
        for (Card card : hand) {
            if (card.getRank().equals(rank)) {
                return true;
            }
        }
        return false;
    }

    public Card giveCardWithRank(String rank) {
        for (Card card : hand) {
            if (card.getRank().equals(rank)) {
                hand.remove(card);
                return card;
            }
        }
        return null;
    }

    public int getScore() {
        return score;
    }
}
