package com.jrichard.logmein.deckofcard.service;

import com.jrichard.logmein.deckofcard.domain.Game;
import com.jrichard.logmein.deckofcard.domain.Hand;
import com.jrichard.logmein.deckofcard.domain.Player;
import com.jrichard.logmein.deckofcard.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    public PlayerRepository playerRepository;

    public void initialize(final String playerName, Game game) {
        final Player player = new Player();
        player.setName(playerName);
        player.setHand(new Hand());
        game.addPlayers(player);
    }


}
