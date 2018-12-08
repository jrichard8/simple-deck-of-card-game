package com.jrichard.logmein.deckofcard.web.rest;

import com.jrichard.logmein.deckofcard.SimpleDeckOfCardGameApp;

import com.jrichard.logmein.deckofcard.domain.Card;
import com.jrichard.logmein.deckofcard.repository.CardRepository;
import com.jrichard.logmein.deckofcard.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
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

import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import com.jrichard.logmein.deckofcard.domain.enumeration.ValueEnum;
/**
 * Test class for the CardResource REST controller.
 *
 * @see CardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleDeckOfCardGameApp.class)
public class CardResourceIntTest {

    private static final SuitEnum DEFAULT_SUIT_TYPE = SuitEnum.CLUB;
    private static final SuitEnum UPDATED_SUIT_TYPE = SuitEnum.DIAMOND;

    private static final ValueEnum DEFAULT_VALUE = ValueEnum.ACE;
    private static final ValueEnum UPDATED_VALUE = ValueEnum.TWO;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCardMockMvc;

    private Card card;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CardResource cardResource = new CardResource(cardRepository);
        this.restCardMockMvc = MockMvcBuilders.standaloneSetup(cardResource)
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
    public static Card createEntity(EntityManager em) {
        Card card = new Card()
            .suitType(DEFAULT_SUIT_TYPE)
            .value(DEFAULT_VALUE);
        return card;
    }

    @Before
    public void initTest() {
        card = createEntity(em);
    }

    @Test
    @Transactional
    public void createCard() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card
        restCardMockMvc.perform(post("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate + 1);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getSuitType()).isEqualTo(DEFAULT_SUIT_TYPE);
        assertThat(testCard.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card with an existing ID
        card.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardMockMvc.perform(post("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSuitTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setSuitType(null);

        // Create the Card, which fails.

        restCardMockMvc.perform(post("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setValue(null);

        // Create the Card, which fails.

        restCardMockMvc.perform(post("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCards() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList
        restCardMockMvc.perform(get("/api/cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].suitType").value(hasItem(DEFAULT_SUIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.suitType").value(DEFAULT_SUIT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card
        Card updatedCard = cardRepository.findById(card.getId()).get();
        // Disconnect from session so that the updates on updatedCard are not directly saved in db
        em.detach(updatedCard);
        updatedCard
            .suitType(UPDATED_SUIT_TYPE)
            .value(UPDATED_VALUE);

        restCardMockMvc.perform(put("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCard)))
            .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getSuitType()).isEqualTo(UPDATED_SUIT_TYPE);
        assertThat(testCard.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Create the Card

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc.perform(put("/api/cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(card)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeDelete = cardRepository.findAll().size();

        // Get the card
        restCardMockMvc.perform(delete("/api/cards/{id}", card.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Card.class);
        Card card1 = new Card();
        card1.setId(1L);
        Card card2 = new Card();
        card2.setId(card1.getId());
        assertThat(card1).isEqualTo(card2);
        card2.setId(2L);
        assertThat(card1).isNotEqualTo(card2);
        card1.setId(null);
        assertThat(card1).isNotEqualTo(card2);
    }
}
