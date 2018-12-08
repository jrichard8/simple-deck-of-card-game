package com.jrichard.logmein.deckofcard.web.rest;

import com.jrichard.logmein.deckofcard.SimpleDeckOfCardGameApp;

import com.jrichard.logmein.deckofcard.domain.Suit;
import com.jrichard.logmein.deckofcard.repository.SuitRepository;
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
/**
 * Test class for the SuitResource REST controller.
 *
 * @see SuitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleDeckOfCardGameApp.class)
public class SuitResourceIntTest {

    private static final SuitEnum DEFAULT_SUIT_TYPE = SuitEnum.CLUB;
    private static final SuitEnum UPDATED_SUIT_TYPE = SuitEnum.DIAMOND;

    @Autowired
    private SuitRepository suitRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSuitMockMvc;

    private Suit suit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuitResource suitResource = new SuitResource(suitRepository);
        this.restSuitMockMvc = MockMvcBuilders.standaloneSetup(suitResource)
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
    public static Suit createEntity(EntityManager em) {
        Suit suit = new Suit()
            .suitType(DEFAULT_SUIT_TYPE);
        return suit;
    }

    @Before
    public void initTest() {
        suit = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuit() throws Exception {
        int databaseSizeBeforeCreate = suitRepository.findAll().size();

        // Create the Suit
        restSuitMockMvc.perform(post("/api/suits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suit)))
            .andExpect(status().isCreated());

        // Validate the Suit in the database
        List<Suit> suitList = suitRepository.findAll();
        assertThat(suitList).hasSize(databaseSizeBeforeCreate + 1);
        Suit testSuit = suitList.get(suitList.size() - 1);
        assertThat(testSuit.getSuitType()).isEqualTo(DEFAULT_SUIT_TYPE);
    }

    @Test
    @Transactional
    public void createSuitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suitRepository.findAll().size();

        // Create the Suit with an existing ID
        suit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuitMockMvc.perform(post("/api/suits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suit)))
            .andExpect(status().isBadRequest());

        // Validate the Suit in the database
        List<Suit> suitList = suitRepository.findAll();
        assertThat(suitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSuitTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = suitRepository.findAll().size();
        // set the field null
        suit.setSuitType(null);

        // Create the Suit, which fails.

        restSuitMockMvc.perform(post("/api/suits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suit)))
            .andExpect(status().isBadRequest());

        List<Suit> suitList = suitRepository.findAll();
        assertThat(suitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuits() throws Exception {
        // Initialize the database
        suitRepository.saveAndFlush(suit);

        // Get all the suitList
        restSuitMockMvc.perform(get("/api/suits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suit.getId().intValue())))
            .andExpect(jsonPath("$.[*].suitType").value(hasItem(DEFAULT_SUIT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getSuit() throws Exception {
        // Initialize the database
        suitRepository.saveAndFlush(suit);

        // Get the suit
        restSuitMockMvc.perform(get("/api/suits/{id}", suit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suit.getId().intValue()))
            .andExpect(jsonPath("$.suitType").value(DEFAULT_SUIT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSuit() throws Exception {
        // Get the suit
        restSuitMockMvc.perform(get("/api/suits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuit() throws Exception {
        // Initialize the database
        suitRepository.saveAndFlush(suit);

        int databaseSizeBeforeUpdate = suitRepository.findAll().size();

        // Update the suit
        Suit updatedSuit = suitRepository.findById(suit.getId()).get();
        // Disconnect from session so that the updates on updatedSuit are not directly saved in db
        em.detach(updatedSuit);
        updatedSuit
            .suitType(UPDATED_SUIT_TYPE);

        restSuitMockMvc.perform(put("/api/suits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSuit)))
            .andExpect(status().isOk());

        // Validate the Suit in the database
        List<Suit> suitList = suitRepository.findAll();
        assertThat(suitList).hasSize(databaseSizeBeforeUpdate);
        Suit testSuit = suitList.get(suitList.size() - 1);
        assertThat(testSuit.getSuitType()).isEqualTo(UPDATED_SUIT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSuit() throws Exception {
        int databaseSizeBeforeUpdate = suitRepository.findAll().size();

        // Create the Suit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuitMockMvc.perform(put("/api/suits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suit)))
            .andExpect(status().isBadRequest());

        // Validate the Suit in the database
        List<Suit> suitList = suitRepository.findAll();
        assertThat(suitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuit() throws Exception {
        // Initialize the database
        suitRepository.saveAndFlush(suit);

        int databaseSizeBeforeDelete = suitRepository.findAll().size();

        // Get the suit
        restSuitMockMvc.perform(delete("/api/suits/{id}", suit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Suit> suitList = suitRepository.findAll();
        assertThat(suitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suit.class);
        Suit suit1 = new Suit();
        suit1.setId(1L);
        Suit suit2 = new Suit();
        suit2.setId(suit1.getId());
        assertThat(suit1).isEqualTo(suit2);
        suit2.setId(2L);
        assertThat(suit1).isNotEqualTo(suit2);
        suit1.setId(null);
        assertThat(suit1).isNotEqualTo(suit2);
    }
}
