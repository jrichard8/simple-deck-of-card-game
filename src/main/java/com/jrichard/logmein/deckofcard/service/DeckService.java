package com.jrichard.logmein.deckofcard.service;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jrichard.logmein.deckofcard.domain.Card;
import com.jrichard.logmein.deckofcard.domain.Deck;
import com.jrichard.logmein.deckofcard.domain.Game;
import com.jrichard.logmein.deckofcard.domain.Suit;
import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import com.jrichard.logmein.deckofcard.domain.enumeration.ValueEnum;
import com.jrichard.logmein.deckofcard.repository.CardRepository;
import com.jrichard.logmein.deckofcard.repository.DeckRepository;
import com.jrichard.logmein.deckofcard.service.exception.NoMoreCardException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeckService {

    @Autowired
    private SuitService suitService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

    public void createNewDeck(Game game) {
        game.addDecks(this.createNewDeck());
    }

    public Deck createNewDeck() {
        final Deck deck = new Deck();
        List<SuitEnum> shuffledSuit = Lists.newArrayList(SuitEnum.values());
        Collections.shuffle(shuffledSuit);
        for (SuitEnum suit : SuitEnum.values()) {
            suitService.initializeSuit(suit, deck);
        }
        return deck;
    }

    public Map<SuitEnum, Integer> cardLeftBySuit(Long deckId) {
        final Map<SuitEnum, Integer> cardLeftBySuit = Maps.newHashMap();
        Optional<Deck> deckOptional = deckRepository.findById(deckId);
        Set<Suit> suits = deckOptional.orElseThrow(IllegalArgumentException::new).getSuits();

        for (Suit suit : suits) {
            List<Card> allBySuit = cardRepository.findAllBySuitIdAndHandIsNull(suit.getId());
            cardLeftBySuit.put(suit.getSuitType(), allBySuit.size());
        }
        return cardLeftBySuit;
    }

    public Map<SuitEnum, List<ValueEnum>> getListOfRemainingCard(Long id) {
        Map<SuitEnum, List<ValueEnum>> remainCardBySuit = Maps.newHashMap();
        Optional<Deck> deckOptional = deckRepository.findById(id);
        Set<Suit> suits = deckOptional.orElseThrow(IllegalArgumentException::new).getSuits();
        for (Suit suit : suits) {
            List<Card> allBySuit = cardRepository
                .findAllBySuitIdAndHandIsNull(suit.getId());
            List<ValueEnum> cardValue = allBySuit.stream().map(c -> c.getValue()).collect(Collectors.toList());
            Collections.sort(cardValue,Comparator.comparingInt(card -> card.getVal()));
            remainCardBySuit.put(suit.getSuitType(), cardValue);
        }
        return remainCardBySuit;
    }
}
