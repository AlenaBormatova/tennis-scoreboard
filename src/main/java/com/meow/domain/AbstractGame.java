package com.meow.domain;

import com.meow.entity.Player;

public abstract class AbstractGame implements Game {

    private static final int MIN_POINT_LEAD = 2;

    private final Player firstPlayer;
    private final Player secondPlayer;
    private int pointFirstPlayer;
    private int pointSecondPlayer;

    protected AbstractGame(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    @Override
    public void awardPoint(Player player) {
        if (isGameOver()) {
            throw new IllegalStateException("Game over");
        }

        if (player.equals(firstPlayer)) {
            pointFirstPlayer++;
        } else if (player.equals(secondPlayer)) {
            pointSecondPlayer++;
        } else {
            throw new IllegalArgumentException("Player is not a participant");
        }
    }

    @Override
    public Player determineWinner() {
        if (!isGameOver()) {
            return null;
        }

        if (pointFirstPlayer > pointSecondPlayer) {
            return firstPlayer;
        } else {
            return secondPlayer;
        }
    }

    @Override
    public boolean isGameOver() {
        return (pointFirstPlayer >= getMinPointsToWin() && (pointFirstPlayer - pointSecondPlayer >= MIN_POINT_LEAD))
                || (pointSecondPlayer >= getMinPointsToWin() && (pointSecondPlayer - pointFirstPlayer >= MIN_POINT_LEAD));
    }

    protected abstract int getMinPointsToWin();

    protected int getPoints(Player player) {
        if (player.equals(firstPlayer)) {
            return pointFirstPlayer;
        } else if (player.equals(secondPlayer)) {
            return pointSecondPlayer;
        } else {
            throw new IllegalArgumentException("Player is not a participant");
        }
    }

    protected int getOpponentPoints(Player player) {
        if (player.equals(firstPlayer)) {
            return pointSecondPlayer;
        } else if (player.equals(secondPlayer)) {
            return pointFirstPlayer;
        } else {
            throw new IllegalArgumentException("Player is not a participant");
        }
    }
}