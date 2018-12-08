package com.jrichard.logmein.deckofcard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jrichard.logmein.deckofcard.domain.Suit;
import com.jrichard.logmein.deckofcard.repository.SuitRepository;
import com.jrichard.logmein.deckofcard.web.rest.errors.BadRequestAlertException;
import com.jrichard.logmein.deckofcard.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Suit.
 */
@RestController
@RequestMapping("/api")
public class SuitResource {

    private final Logger log = LoggerFactory.getLogger(SuitResource.class);

    private static final String ENTITY_NAME = "suit";

    private final SuitRepository suitRepository;

    public SuitResource(SuitRepository suitRepository) {
        this.suitRepository = suitRepository;
    }

    /**
     * POST  /suits : Create a new suit.
     *
     * @param suit the suit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suit, or with status 400 (Bad Request) if the suit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suits")
    @Timed
    public ResponseEntity<Suit> createSuit(@Valid @RequestBody Suit suit) throws URISyntaxException {
        log.debug("REST request to save Suit : {}", suit);
        if (suit.getId() != null) {
            throw new BadRequestAlertException("A new suit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Suit result = suitRepository.save(suit);
        return ResponseEntity.created(new URI("/api/suits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suits : Updates an existing suit.
     *
     * @param suit the suit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suit,
     * or with status 400 (Bad Request) if the suit is not valid,
     * or with status 500 (Internal Server Error) if the suit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suits")
    @Timed
    public ResponseEntity<Suit> updateSuit(@Valid @RequestBody Suit suit) throws URISyntaxException {
        log.debug("REST request to update Suit : {}", suit);
        if (suit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Suit result = suitRepository.save(suit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suits : get all the suits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of suits in body
     */
    @GetMapping("/suits")
    @Timed
    public List<Suit> getAllSuits() {
        log.debug("REST request to get all Suits");
        return suitRepository.findAll();
    }

    /**
     * GET  /suits/:id : get the "id" suit.
     *
     * @param id the id of the suit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suit, or with status 404 (Not Found)
     */
    @GetMapping("/suits/{id}")
    @Timed
    public ResponseEntity<Suit> getSuit(@PathVariable Long id) {
        log.debug("REST request to get Suit : {}", id);
        Optional<Suit> suit = suitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(suit);
    }

    /**
     * DELETE  /suits/:id : delete the "id" suit.
     *
     * @param id the id of the suit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suits/{id}")
    @Timed
    public ResponseEntity<Void> deleteSuit(@PathVariable Long id) {
        log.debug("REST request to delete Suit : {}", id);

        suitRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
