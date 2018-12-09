package com.jrichard.logmein.deckofcard.service;

import com.jrichard.logmein.deckofcard.domain.Deck;
import com.jrichard.logmein.deckofcard.domain.Suit;
import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SuitService {

    @Autowired
    private CardService cardService;

    public void initializeSuit(SuitEnum suitType, Deck deck){
        final Suit suit = new Suit();
        suit.setSuitType(suitType);
        cardService.getAllCardOfSuit(suitType, suit);
        deck.addSuits(suit);
    }

}
