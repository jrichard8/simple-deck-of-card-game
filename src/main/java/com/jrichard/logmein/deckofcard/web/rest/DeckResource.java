package com.jrichard.logmein.deckofcard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jrichard.logmein.deckofcard.domain.Deck;
import com.jrichard.logmein.deckofcard.repository.DeckRepository;
import com.jrichard.logmein.deckofcard.web.rest.errors.BadRequestAlertException;
import com.jrichard.logmein.deckofcard.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Deck.
 */
@RestController
@RequestMapping("/api")
public class DeckResource {

    private final Logger log = LoggerFactory.getLogger(DeckResource.class);

    private static final String ENTITY_NAME = "deck";

    private final DeckRepository deckRepository;

    public DeckResource(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    /**
     * POST  /decks : Create a new deck.
     *
     * @param deck the deck to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deck, or with status 400 (Bad Request) if the deck has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/decks")
    @Timed
    public ResponseEntity<Deck> createDeck(@RequestBody Deck deck) throws URISyntaxException {
        log.debug("REST request to save Deck : {}", deck);
        if (deck.getId() != null) {
            throw new BadRequestAlertException("A new deck cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deck result = deckRepository.save(deck);
        return ResponseEntity.created(new URI("/api/decks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /decks : Updates an existing deck.
     *
     * @param deck the deck to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deck,
     * or with status 400 (Bad Request) if the deck is not valid,
     * or with status 500 (Internal Server Error) if the deck couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/decks")
    @Timed
    public ResponseEntity<Deck> updateDeck(@RequestBody Deck deck) throws URISyntaxException {
        log.debug("REST request to update Deck : {}", deck);
        if (deck.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Deck result = deckRepository.save(deck);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deck.getId().toString()))
            .body(result);
    }

    /**
     * GET  /decks : get all the decks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of decks in body
     */
    @GetMapping("/decks")
    @Timed
    public List<Deck> getAllDecks() {
        log.debug("REST request to get all Decks");
        return deckRepository.findAll();
    }

    /**
     * GET  /decks/:id : get the "id" deck.
     *
     * @param id the id of the deck to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deck, or with status 404 (Not Found)
     */
    @GetMapping("/decks/{id}")
    @Timed
    public ResponseEntity<Deck> getDeck(@PathVariable Long id) {
        log.debug("REST request to get Deck : {}", id);
        Optional<Deck> deck = deckRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deck);
    }

    /**
     * DELETE  /decks/:id : delete the "id" deck.
     *
     * @param id the id of the deck to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/decks/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeck(@PathVariable Long id) {
        log.debug("REST request to delete Deck : {}", id);

        deckRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
