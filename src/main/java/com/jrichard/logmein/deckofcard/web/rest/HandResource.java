package com.jrichard.logmein.deckofcard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jrichard.logmein.deckofcard.domain.Hand;
import com.jrichard.logmein.deckofcard.repository.HandRepository;
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
 * REST controller for managing Hand.
 */
@RestController
@RequestMapping("/api")
public class HandResource {

    private final Logger log = LoggerFactory.getLogger(HandResource.class);

    private static final String ENTITY_NAME = "hand";

    private final HandRepository handRepository;

    public HandResource(HandRepository handRepository) {
        this.handRepository = handRepository;
    }

    /**
     * POST  /hands : Create a new hand.
     *
     * @param hand the hand to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hand, or with status 400 (Bad Request) if the hand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hands")
    @Timed
    public ResponseEntity<Hand> createHand(@RequestBody Hand hand) throws URISyntaxException {
        log.debug("REST request to save Hand : {}", hand);
        if (hand.getId() != null) {
            throw new BadRequestAlertException("A new hand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hand result = handRepository.save(hand);
        return ResponseEntity.created(new URI("/api/hands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hands : Updates an existing hand.
     *
     * @param hand the hand to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hand,
     * or with status 400 (Bad Request) if the hand is not valid,
     * or with status 500 (Internal Server Error) if the hand couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hands")
    @Timed
    public ResponseEntity<Hand> updateHand(@RequestBody Hand hand) throws URISyntaxException {
        log.debug("REST request to update Hand : {}", hand);
        if (hand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hand result = handRepository.save(hand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hand.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hands : get all the hands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hands in body
     */
    @GetMapping("/hands")
    @Timed
    public List<Hand> getAllHands() {
        log.debug("REST request to get all Hands");
        return handRepository.findAll();
    }

    /**
     * GET  /hands/:id : get the "id" hand.
     *
     * @param id the id of the hand to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hand, or with status 404 (Not Found)
     */
    @GetMapping("/hands/{id}")
    @Timed
    public ResponseEntity<Hand> getHand(@PathVariable Long id) {
        log.debug("REST request to get Hand : {}", id);
        Optional<Hand> hand = handRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hand);
    }

    /**
     * DELETE  /hands/:id : delete the "id" hand.
     *
     * @param id the id of the hand to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hands/{id}")
    @Timed
    public ResponseEntity<Void> deleteHand(@PathVariable Long id) {
        log.debug("REST request to delete Hand : {}", id);

        handRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
