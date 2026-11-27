package com.meow.domain;

import com.meow.entity.Player;
import lombok.Getter;

import java.util.Optional;

public class Game {

    private static final int ADVANTAGE_LEAD = 1;

    private final Player firstPlayer;
    private final Player secondPlayer;
    private final int minPointsToWin;
    private final int minPointLead;
    @Getter
    private final boolean isTieBreak;
    private int firstPlayerPoints;
    private int secondPlayerPoints;


    public Game(Player firstPlayer, Player secondPlayer, int minPointsToWin, int minPointLead, boolean isTieBreak) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.minPointsToWin = minPointsToWin;
        this.minPointLead = minPointLead;
        this.isTieBreak = isTieBreak;
    }

    public void awardPoint(Player player) {
        if (isGameOver()) {
            throw new IllegalStateException("Game over");
        }

        if (player.equals(firstPlayer)) {
            firstPlayerPoints++;
        } else if (player.equals(secondPlayer)) {
            secondPlayerPoints++;
        } else {
            throw new IllegalArgumentException("Player is not a participant");
        }
    }

    public Optional<Player> determineWinner() {
        if (!isGameOver()) {
            return Optional.empty();
        }

        if (firstPlayerPoints > secondPlayerPoints) {
            return Optional.of(firstPlayer);
        }

        return Optional.of(secondPlayer);
    }

    public boolean isGameOver() {
        return (firstPlayerPoints >= minPointsToWin && (firstPlayerPoints - secondPlayerPoints >= minPointLead))
                || (secondPlayerPoints >= minPointsToWin && (secondPlayerPoints - firstPlayerPoints >= minPointLead));
    }

    public String getScore(Player player) {
        if (isTieBreak) {
            return String.valueOf(getPoints(player));
        }

        int points = getPoints(player);
        int opponentPoints = getOpponentPoints(player);

        switch (points) {
            case 0:
                return "0";
            case 1:
                return "15";
            case 2:
                return "30";
            default:
                if (points >= minPointsToWin && (points - opponentPoints == ADVANTAGE_LEAD)) {
                    return "AD";
                }
                return "40";
        }
    }

    private int getPoints(Player player) {
        if (player.equals(firstPlayer)) {
            return firstPlayerPoints;
        }

        if (player.equals(secondPlayer)) {
            return secondPlayerPoints;
        }

        throw new IllegalArgumentException("Player is not a participant");
    }

    private int getOpponentPoints(Player player) {
        if (player.equals(firstPlayer)) {
            return secondPlayerPoints;
        }

        if (player.equals(secondPlayer)) {
            return firstPlayerPoints;
        }

        throw new IllegalArgumentException("Player is not a participant");
    }
}