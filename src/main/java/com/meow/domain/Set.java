package com.meow.domain;

import com.meow.entity.Player;

import java.util.Optional;

public class Set {

    private static final int REGULAR_GAME_MIN_POINTS = 4;
    private static final int TIE_BREAK_MIN_POINTS = 7;
    private static final int MIN_POINT_LEAD = 2;
    private static final int MIN_GAMES_TO_WIN = 6;
    private static final int MIN_GAME_LEAD = 2;
    private static final int TIE_BREAK_THRESHOLD = 6;
    private static final int TIE_BREAK_WINNING_GAMES = 7;

    private final Player firstPlayer;
    private final Player secondPlayer;
    private Game currentGame;
    private int firstPlayerGames;
    private int secondPlayerGames;

    public Set(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        currentGame = new Game(firstPlayer, secondPlayer, REGULAR_GAME_MIN_POINTS, MIN_POINT_LEAD, false);
    }

    public void awardPoint(Player player) {
        if (isSetOver()) {
            throw new IllegalStateException("Set over");
        }

        currentGame.awardPoint(player);

        if (currentGame.isGameOver()) {

            Player winner = currentGame.determineWinner()
                    .orElseThrow(() -> new IllegalStateException("Completed game must have a winner"));

            if (winner.equals(firstPlayer)) {
                firstPlayerGames++;
            } else {
                secondPlayerGames++;
            }

            if (!isSetOver()) {
                if (firstPlayerGames == TIE_BREAK_THRESHOLD && secondPlayerGames == TIE_BREAK_THRESHOLD) {
                    currentGame = new Game(firstPlayer, secondPlayer, TIE_BREAK_MIN_POINTS, MIN_POINT_LEAD, true);
                } else {
                    currentGame = new Game(firstPlayer, secondPlayer, REGULAR_GAME_MIN_POINTS, MIN_POINT_LEAD, false);
                }
            }
        }
    }

    public Optional<Player> determineWinner() {
        if (!isSetOver()) {
            return Optional.empty();
        }

        if (firstPlayerGames > secondPlayerGames) {
            return Optional.of(firstPlayer);
        }

        return Optional.of(secondPlayer);
    }

    public boolean isSetOver() {
        return (firstPlayerGames >= MIN_GAMES_TO_WIN && (firstPlayerGames - secondPlayerGames >= MIN_GAME_LEAD))
                || (secondPlayerGames >= MIN_GAMES_TO_WIN && (secondPlayerGames - firstPlayerGames >= MIN_GAME_LEAD))
                || (firstPlayerGames == TIE_BREAK_WINNING_GAMES && secondPlayerGames == TIE_BREAK_THRESHOLD)
                || (secondPlayerGames == TIE_BREAK_WINNING_GAMES && firstPlayerGames == TIE_BREAK_THRESHOLD);
    }

    public String getScore(Player player) {
        return currentGame.getScore(player);
    }

    public int getGames(Player player) {
        if (player.equals(firstPlayer)) {
            return firstPlayerGames;
        }

        if (player.equals(secondPlayer)) {
            return secondPlayerGames;
        }

        throw new IllegalArgumentException("Player is not a participant");
    }

    public boolean isTieBreak() {
        return currentGame.isTieBreak();
    }
}