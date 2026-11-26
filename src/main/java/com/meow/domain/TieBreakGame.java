package com.meow.domain;

import com.meow.entity.Player;

public class TieBreakGame extends AbstractGame {

    private static final int MIN_POINTS_TO_WIN = 7;

    public TieBreakGame(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
    }

    @Override
    public String getScore(Player player) {
        return String.valueOf(getPoints(player));
    }

    @Override
    protected int getMinPointsToWin() {
        return MIN_POINTS_TO_WIN;
    }
}