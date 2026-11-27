package com.meow.domain;

import com.meow.entity.Player;
import lombok.Getter;

import java.util.Optional;

public class Match {

    private static final int MIN_SETS_TO_WIN = 2;

    @Getter
    private final Player firstPlayer;
    @Getter
    private final Player secondPlayer;
    private Set currentSet;
    private int firstPlayerSets;
    private int secondPlayerSets;

    public Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        currentSet = new Set(firstPlayer, secondPlayer);
    }

    public void awardPoint(Player player) {
        if (isMatchOver()) {
            throw new IllegalStateException("Match over");
        }

        currentSet.awardPoint(player);

        if (currentSet.isSetOver()) {

            Player winner = currentSet.determineWinner()
                    .orElseThrow(() -> new IllegalStateException("Completed set must have a winner"));

            if (winner.equals(firstPlayer)) {
                firstPlayerSets++;
            } else {
                secondPlayerSets++;
            }

            if (!isMatchOver()) {
                currentSet = new Set(firstPlayer, secondPlayer);
            }
        }
    }

    public Optional<Player> determineWinner() {
        if (!isMatchOver()) {
            return Optional.empty();
        }

        if (firstPlayerSets > secondPlayerSets) {
            return Optional.of(firstPlayer);
        }

        return Optional.of(secondPlayer);

    }

    public boolean isMatchOver() {
        return firstPlayerSets >= MIN_SETS_TO_WIN || secondPlayerSets >= MIN_SETS_TO_WIN;
    }

    public int getSets(Player player) {
        if (player.equals(firstPlayer)) {
            return firstPlayerSets;
        }

        if (player.equals(secondPlayer)) {
            return secondPlayerSets;
        }

        throw new IllegalArgumentException("Player is not a participant");
    }

    public int getGames(Player player) {
        return currentSet.getGames(player);
    }

    public String getScore(Player player) {
        return currentSet.getScore(player);
    }

    public boolean isTieBreak() {
        return currentSet.isTieBreak();
    }
}