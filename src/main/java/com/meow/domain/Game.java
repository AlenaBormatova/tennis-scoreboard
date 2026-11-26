package com.meow.domain;

import com.meow.entity.Player;

public interface Game {

    void awardPoint(Player player);

    boolean isGameOver();

    Player determineWinner();

    String getScore(Player player);

}