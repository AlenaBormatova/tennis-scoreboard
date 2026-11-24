package com.meow.dao;

import com.meow.entity.Match;

import java.util.List;

public interface MatchDao {

    Match save(Match match);

    List<Match> findAll(int page, int pageSize);

    List<Match> findByPlayerName(String name, int page, int pageSize);

    Long count();

    Long countByPlayerName(String name);
}