package com.meow.service;

import com.meow.domain.Match;
import com.meow.entity.Player;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OngoingMatchesService {

    private final Map<UUID, Match> ongoingMatches = new ConcurrentHashMap<>();

    public UUID createMatch(Player firstPlayer, Player secondPlayer) {

        Match match = new Match(firstPlayer, secondPlayer);
        UUID uuid = UUID.randomUUID();

        ongoingMatches.put(uuid, match);

        return uuid;
    }

    public Optional<Match> getMatch(UUID uuid) {
        return Optional.ofNullable(ongoingMatches.get(uuid));
    }

    public void removeMatch(UUID uuid) {
        ongoingMatches.remove(uuid);
    }
}