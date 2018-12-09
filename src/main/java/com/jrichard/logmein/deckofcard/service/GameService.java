package com.jrichard.logmein.deckofcard.service;

import com.jrichard.logmein.deckofcard.domain.*;
import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import com.jrichard.logmein.deckofcard.repository.*;
import com.jrichard.logmein.deckofcard.security.SecurityUtils;
import com.jrichard.logmein.deckofcard.service.dto.GameDTO;
import com.jrichard.logmein.deckofcard.service.dto.PlayerDTO;
import com.jrichard.logmein.deckofcard.service.exception.PlayerNotInGameException;
import com.jrichard.logmein.deckofcard.service.mapper.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (!gameById.isPresent()) {
            throw new IllegalArgumentException();
        }
        final Game game = gameById.get();
        return game;
    }

    public GameDTO dealCard(Long playerId) throws PlayerNotInGameException, OperationNotSupportedException {
        final Optional<Player> playerOptional = playerRepository.findById(playerId);

        Player player = playerOptional.orElseThrow(IllegalArgumentException::new);
        Game game = player.getGame();
        if (game == null) {
            throw new PlayerNotInGameException();
        }
        List<Card> allByHandIsNull = carRepository.findAllByHandIsNull();

        Collections.shuffle(allByHandIsNull);
        Hand hand = handRepository.save(new Hand());
        if(player.getHand() == null) {
            player.setHand(hand);
        }
        player.getHand().addCards(allByHandIsNull.get(0));
        playerService.computeSumOfCard(player);
        playerRepository.save(player);
        return GameMapper.INSTANCE.gameToGameDto(game);
    }

    public List<PlayerDTO> getPlayerByCountOrder(Long gameId) {
        List<Player> allByGameIdOrderBySumOfCardValue = playerRepository.findAllByGameIdOrderBySumOfCardValueDesc(gameId);
        return allByGameIdOrderBySumOfCardValue
            .stream()
            .map(player -> GameMapper.INSTANCE.playerToPlayerDto(player))
            .collect(Collectors.toList());
    }



}
