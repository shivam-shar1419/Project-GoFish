/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author gursegur
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GoFishGame extends Game {
    private final Deck deck;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private final Scanner scanner;

    public GoFishGame() {
        super("Go Fish");
        deck = new Deck();
        deck.shuffle();
        players = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Starting Go Fish game...");
        registerPlayers();
        dealInitialCards();

        while (!isGameOver()) {
            for (Player player : players) {
                currentPlayer = player;
                if (currentPlayer.getHand().isEmpty() && deck.remainingCards() > 0) {
                    currentPlayer.addCardToHand(deck.drawCard());
                }
                playTurn();
                checkForBooks(currentPlayer);
                if (isGameOver()) break;
            }
        }
        declareWinner();
    }

    @Override
    public void declareWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        System.out.println("The winner is " + winner.getName() + " with a score of " + winner.getScore() + "!");
    }

    private void registerPlayers() {
        System.out.println("Enter number of players:");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 1; i <= numPlayers; i++) {
            System.out.println("Enter name for player " + i + ":");
            String name = scanner.nextLine();
            addPlayer(name);
        }
    }

    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    private void dealInitialCards() {
        int initialCardsCount = 7;
        for (Player player : players) {
            for (int i = 0; i < initialCardsCount; i++) {
                player.addCardToHand(deck.drawCard());
            }
        }
    }

    private void playTurn() {
        System.out.println(currentPlayer.getName() + "'s turn.");
        System.out.println("Your hand: " + currentPlayer.getHand());
        displayScores();

        Player targetPlayer = choosePlayer();
        String rank = chooseRank();

        if (targetPlayer.hasCardWithRank(rank)) {
            System.out.println(targetPlayer.getName() + " has the card!");
            Card card;
            while ((card = targetPlayer.giveCardWithRank(rank)) != null) {
                currentPlayer.addCardToHand(card);
            }
        } else {
            System.out.println("Go Fish!");
            Card drawnCard = deck.drawCard();
            if (drawnCard != null) {
                currentPlayer.addCardToHand(drawnCard);
            }
        }
        checkForBooks(currentPlayer); // Check for books at the end of the turn
    }

    private Player choosePlayer() {
        System.out.println("Choose a player to ask for a card:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println(i + 1 + ". " + players.get(i).getName());
        }
        int choice = scanner.nextInt() - 1;
        return players.get(choice);
    }

    private String chooseRank() {
        System.out.println("Choose a rank to ask for:");
        scanner.nextLine(); // Consume newline
        return scanner.nextLine();
    }

    private void checkForBooks(Player player) {
        ArrayList<Card> hand = player.getHand();
        HashMap<String, Integer> rankCounts = new HashMap<>();
        for (Card card : hand) {
            String rank = card.getRank();
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
        }
        System.out.println(player.getName() + "'s hand: " + hand);
        System.out.println("Rank counts: " + rankCounts);
        for (String rank : rankCounts.keySet()) {
            if (rankCounts.get(rank) == 4) {
                System.out.println(player.getName() + " has a book of " + rank + "s!");
                player.addBook(rank);
                hand.removeIf(c -> c.getRank().equals(rank));
            }
        }
    }

    private boolean isGameOver() {
        if (deck.remainingCards() > 0) {
            return false;
        }
        for (Player player : players) {
            if (!player.getHand().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void displayScores() {
        System.out.println("Current scores:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
        }
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
