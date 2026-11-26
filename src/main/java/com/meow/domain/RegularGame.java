package com.meow.domain;

import com.meow.entity.Player;

public class RegularGame extends AbstractGame {

    private static final int MIN_POINTS_TO_WIN = 4;
    private static final int ADVANTAGE_LEAD = 1;

    public RegularGame(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
    }

    @Override
    public String getScore(Player player) {
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
                if (points >= MIN_POINTS_TO_WIN && (points - opponentPoints == ADVANTAGE_LEAD)) {
                    return "AD";
                } else {
                    return "40";
                }
        }
    }

    @Override
    protected int getMinPointsToWin() {
        return MIN_POINTS_TO_WIN;
    }
}