package com.meow.domain;

import com.meow.entity.Player;

public class Set {

    private static final int MIN_GAMES_TO_WIN = 6;
    private static final int MIN_GAME_LEAD = 2;
    private static final int TIE_BREAK_THRESHOLD = 6;
    private static final int TIE_BREAK_WINNING_GAMES = 7;

    private Game currentGame;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private int firstPlayerGames;
    private int secondPlayerGames;

    public Set(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        currentGame = new RegularGame(firstPlayer, secondPlayer);
    }

    public void awardPoint(Player player) {
        if (isSetOver()) {
            throw new IllegalStateException("Set over");
        }

        currentGame.awardPoint(player);

        if (currentGame.isGameOver()) {
            Player winner = currentGame.determineWinner();
            if (winner.equals(firstPlayer)) {
                firstPlayerGames++;
            } else {
                secondPlayerGames++;
            }

            if (!isSetOver()) {
                if (firstPlayerGames == TIE_BREAK_THRESHOLD && secondPlayerGames == TIE_BREAK_THRESHOLD) {
                    currentGame = new TieBreakGame(firstPlayer, secondPlayer);
                } else {
                    currentGame = new RegularGame(firstPlayer, secondPlayer);
                }
            }
        }
    }

    public Player determineWinner() {
        if (!isSetOver()) {
            return null;
        }

        if (firstPlayerGames > secondPlayerGames) {
            return firstPlayer;
        } else {
            return secondPlayer;
        }
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
        } else if (player.equals(secondPlayer)) {
            return secondPlayerGames;
        } else {
            throw new IllegalArgumentException("Player is not a participant");
        }
    }

    public boolean isTieBreak() {
        return currentGame instanceof TieBreakGame;
    }
}