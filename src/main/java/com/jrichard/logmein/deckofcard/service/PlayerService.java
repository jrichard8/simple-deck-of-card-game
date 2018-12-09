package com.jrichard.logmein.deckofcard.service;

import com.jrichard.logmein.deckofcard.domain.Card;
import com.jrichard.logmein.deckofcard.domain.Game;
import com.jrichard.logmein.deckofcard.domain.Hand;
import com.jrichard.logmein.deckofcard.domain.Player;
import com.jrichard.logmein.deckofcard.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.Optional;
import java.util.Set;

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


    public void computeSumOfCard(Player player) throws OperationNotSupportedException {
        Hand hand = player.getHand();
        Set<Card> cards = Optional.ofNullable(hand).orElse(new Hand()).getCards();
        Optional<Integer> reduce = cards.stream().map(c -> c.getValue().getVal()).reduce((a, b) -> a + b);
        player.setSumOfCardValue(reduce.orElseThrow(OperationNotSupportedException::new));
    }
}
