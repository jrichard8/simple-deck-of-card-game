package com.jrichard.logmein.deckofcard.service;


import com.jrichard.logmein.deckofcard.domain.Deck;
import com.jrichard.logmein.deckofcard.domain.Game;
import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import com.jrichard.logmein.deckofcard.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckService {

    @Autowired
    private SuitService suitService;

    public void createNewDeck(Game game) {
        final Deck deck = new Deck();
        for (SuitEnum suit : SuitEnum.values()) {
            suitService.initializeSuit(suit, deck);
        }
        game.addDecks(deck);
    }



}
