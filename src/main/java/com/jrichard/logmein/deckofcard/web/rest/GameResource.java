package com.jrichard.logmein.deckofcard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jrichard.logmein.deckofcard.domain.Game;
import com.jrichard.logmein.deckofcard.domain.Player;
import com.jrichard.logmein.deckofcard.repository.GameRepository;
import com.jrichard.logmein.deckofcard.service.GameService;
import com.jrichard.logmein.deckofcard.service.dto.GameDTO;
import com.jrichard.logmein.deckofcard.service.dto.PlayerDTO;
import com.jrichard.logmein.deckofcard.service.exception.NoMoreCardException;
import com.jrichard.logmein.deckofcard.service.exception.PlayerNotInGameException;
import com.jrichard.logmein.deckofcard.service.mapper.GameMapper;
import com.jrichard.logmein.deckofcard.web.rest.errors.BadRequestAlertException;
import com.jrichard.logmein.deckofcard.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Game.
 */
@RestController
@RequestMapping("/api")
public class GameResource {

    private final Logger log = LoggerFactory.getLogger(GameResource.class);

    private static final String ENTITY_NAME = "game";

    private final GameRepository gameRepository;

    public GameResource(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public GameService gameService;

    /**
     * POST  /games : Create a new game.
     *
     * @param game the game to create
     * @return the ResponseEntity with status 201 (Created) and with body the new game, or with status 400 (Bad Request) if the game has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/games")
    @Timed
    public ResponseEntity<GameDTO> createGame(@AuthenticationPrincipal User user, @Valid @RequestBody Game game) throws URISyntaxException {
        log.debug("REST request to save Game : {} with user: {}", game, user);
        if (game.getId() != null) {
            throw new BadRequestAlertException("A new game cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gameService.initializeGame(game);
        final Game result = gameService.saveAll(game);
        final GameDTO returnedData = GameMapper.INSTANCE.gameToGameDto(game);
        return ResponseEntity.created(new URI("/api/games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(returnedData);
    }

    /**
     * PUT  /games : Updates an existing game.
     *
     * @param game the game to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated game,
     * or with status 400 (Bad Request) if the game is not valid,
     * or with status 500 (Internal Server Error) if the game couldn't be updated
     */
    @PutMapping("/games")
    @Timed
    @Transactional
    public ResponseEntity<GameDTO> updateGame(@Valid @RequestBody Game game) {
        log.debug("REST request to update Game : {}", game);
        if (game.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        gameRepository.save(game);
        final GameDTO returnedData = GameMapper.INSTANCE.gameToGameDto(game);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, game.getId().toString()))
            .body(returnedData);
    }

    /**
     * GET  /games : get all the games.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of games in body
     */
    @GetMapping("/games")
    @Timed
    @Transactional
    public List<GameDTO> getAllGames() {
        log.debug("REST request to get all Games");
        List<Game> all = gameRepository.findAll();
        List<GameDTO> collect = all.stream()
            .map(game -> GameMapper.INSTANCE.gameToGameDto(game))
            .collect(Collectors.toList());
        return collect;
    }

    /**
     * GET  /games/:id : get the "id" game.
     *
     * @param id the id of the game to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the game, or with status 404 (Not Found)
     */
    @GetMapping("/games/{id}")
    @Timed
    @Transactional
    public ResponseEntity<GameDTO> getGame(@PathVariable Long id) {
        log.debug("REST request to get Game : {}", id);
        Optional<Game> gameOptional = gameRepository.findById(id);
        if(!gameOptional.isPresent()) {
            throw new IllegalArgumentException();
        }
        Game game = gameOptional.get();
        GameDTO gameDTO = GameMapper.INSTANCE.gameToGameDto(game);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, game.getId().toString()))
            .body(gameDTO);
    }

    /**
     * DELETE  /games/:id : delete the "id" game.
     *
     * @param id the id of the game to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/games/{id}")
    @Timed
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        log.debug("REST request to delete Game : {}", id);

        gameRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/games/dealcard/{playerId}")
    @Timed
    @Transactional
    public ResponseEntity<GameDTO> dealCard(@PathVariable Long playerId) throws PlayerNotInGameException, OperationNotSupportedException {
        GameDTO gameDTO = gameService.dealCard(playerId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gameDTO.getId().toString()))
            .body(gameDTO);
    }

    @GetMapping("/games/players/ordered/{gameId}")
    @Timed
    @Transactional
    public List<PlayerDTO> getPlayerByCountOrder(@PathVariable Long gameId) {
        return gameService.getPlayerByCountOrder(gameId);
    }


}
