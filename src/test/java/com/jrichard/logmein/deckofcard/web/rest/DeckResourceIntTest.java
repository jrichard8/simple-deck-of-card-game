package com.jrichard.logmein.deckofcard.web.rest;

import com.jrichard.logmein.deckofcard.SimpleDeckOfCardGameApp;

import com.jrichard.logmein.deckofcard.domain.Deck;
import com.jrichard.logmein.deckofcard.repository.DeckRepository;
import com.jrichard.logmein.deckofcard.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.jrichard.logmein.deckofcard.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeckResource REST controller.
 *
 * @see DeckResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleDeckOfCardGameApp.class)
@Ignore
public class DeckResourceIntTest {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeckMockMvc;

    private Deck deck;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeckResource deckResource = new DeckResource(deckRepository);
        this.restDeckMockMvc = MockMvcBuilders.standaloneSetup(deckResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deck createEntity(EntityManager em) {
        Deck deck = new Deck();
        return deck;
    }

    @Before
    public void initTest() {
        deck = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeck() throws Exception {
        int databaseSizeBeforeCreate = deckRepository.findAll().size();

        // Create the Deck
        restDeckMockMvc.perform(post("/api/decks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deck)))
            .andExpect(status().isCreated());

        // Validate the Deck in the database
        List<Deck> deckList = deckRepository.findAll();
        assertThat(deckList).hasSize(databaseSizeBeforeCreate + 1);
        Deck testDeck = deckList.get(deckList.size() - 1);
    }

    @Test
    @Transactional
    public void createDeckWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deckRepository.findAll().size();

        // Create the Deck with an existing ID
        deck.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeckMockMvc.perform(post("/api/decks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deck)))
            .andExpect(status().isBadRequest());

        // Validate the Deck in the database
        List<Deck> deckList = deckRepository.findAll();
        assertThat(deckList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDecks() throws Exception {
        // Initialize the database
        deckRepository.saveAndFlush(deck);

        // Get all the deckList
        restDeckMockMvc.perform(get("/api/decks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deck.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDeck() throws Exception {
        // Initialize the database
        deckRepository.saveAndFlush(deck);

        // Get the deck
        restDeckMockMvc.perform(get("/api/decks/{id}", deck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deck.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDeck() throws Exception {
        // Get the deck
        restDeckMockMvc.perform(get("/api/decks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeck() throws Exception {
        // Initialize the database
        deckRepository.saveAndFlush(deck);

        int databaseSizeBeforeUpdate = deckRepository.findAll().size();

        // Update the deck
        Deck updatedDeck = deckRepository.findById(deck.getId()).get();
        // Disconnect from session so that the updates on updatedDeck are not directly saved in db
        em.detach(updatedDeck);

        restDeckMockMvc.perform(put("/api/decks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeck)))
            .andExpect(status().isOk());

        // Validate the Deck in the database
        List<Deck> deckList = deckRepository.findAll();
        assertThat(deckList).hasSize(databaseSizeBeforeUpdate);
        Deck testDeck = deckList.get(deckList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDeck() throws Exception {
        int databaseSizeBeforeUpdate = deckRepository.findAll().size();

        // Create the Deck

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeckMockMvc.perform(put("/api/decks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deck)))
            .andExpect(status().isBadRequest());

        // Validate the Deck in the database
        List<Deck> deckList = deckRepository.findAll();
        assertThat(deckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeck() throws Exception {
        // Initialize the database
        deckRepository.saveAndFlush(deck);

        int databaseSizeBeforeDelete = deckRepository.findAll().size();

        // Get the deck
        restDeckMockMvc.perform(delete("/api/decks/{id}", deck.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Deck> deckList = deckRepository.findAll();
        assertThat(deckList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deck.class);
        Deck deck1 = new Deck();
        deck1.setId(1L);
        Deck deck2 = new Deck();
        deck2.setId(deck1.getId());
        assertThat(deck1).isEqualTo(deck2);
        deck2.setId(2L);
        assertThat(deck1).isNotEqualTo(deck2);
        deck1.setId(null);
        assertThat(deck1).isNotEqualTo(deck2);
    }
}
