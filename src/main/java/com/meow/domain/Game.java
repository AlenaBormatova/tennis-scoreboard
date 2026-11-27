package com.meow.domain;

import com.meow.entity.Player;

import java.util.Optional;

public interface Game {

    void awardPoint(Player player);

    boolean isGameOver();

    Optional<Player> determineWinner();

    String getScore(Player player);

}