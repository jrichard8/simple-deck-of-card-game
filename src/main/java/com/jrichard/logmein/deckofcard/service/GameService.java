package com.jrichard.logmein.deckofcard.service;

import com.jrichard.logmein.deckofcard.domain.Deck;
import com.jrichard.logmein.deckofcard.domain.Game;
import com.jrichard.logmein.deckofcard.domain.Player;
import com.jrichard.logmein.deckofcard.domain.Suit;
import com.jrichard.logmein.deckofcard.repository.*;
import com.jrichard.logmein.deckofcard.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    public PlayerService playerService;
    @Autowired
    public DeckService deckService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private HandRepository handRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private SuitRepository suitRepository;
    @Autowired
    private CardRepository carRepository;



    public void initializeGame(Game game) {
        final String userName = SecurityUtils.getCurrentUserLogin().orElseThrow(IllegalStateException::new);
        playerService.initialize(userName, game);
        deckService.createNewDeck(game);
    }

    public Game saveAll(Game game) {
        gameRepository.save(game);
        for (Player player : game.getPlayers()) {
            handRepository.save(player.getHand());
            playerRepository.save(player);
        }
        for (Deck deck : game.getDecks()) {
            deckRepository.save(deck);
            for (Suit suit : deck.getSuits()) {
                suitRepository.save(suit);
                suit.getCards().forEach(card -> carRepository.save(card));
            }
        }
        return game;
    }



    public Game get(final Long id) {
        Optional<Game> gameById = gameRepository.findById(id);
        if(!gameById.isPresent()) {
            throw new IllegalArgumentException();
        }
        final Game game = gameById.get();
        return game;
    }

}
