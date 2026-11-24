package com.meow.dao;

import com.meow.entity.Player;

import java.util.Optional;

public interface PlayerDao {

    Optional<Player> findByName(String name);

    Player save(Player player);
}
