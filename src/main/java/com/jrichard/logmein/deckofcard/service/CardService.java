package com.jrichard.logmein.deckofcard.service;

import com.jrichard.logmein.deckofcard.domain.Card;
import com.jrichard.logmein.deckofcard.domain.Suit;
import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import com.jrichard.logmein.deckofcard.domain.enumeration.ValueEnum;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    public void getAllCardOfSuit(SuitEnum suitType, Suit suit) {
        for (ValueEnum value : ValueEnum.values()) {
            final Card card = new Card();
            card.setValue(value);
            card.setSuitType(suitType);
            suit.addCards(card);
        }
    }

}
