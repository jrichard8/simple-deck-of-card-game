package com.jrichard.logmein.deckofcard.service;

import com.google.common.collect.Lists;
import com.jrichard.logmein.deckofcard.domain.Card;
import com.jrichard.logmein.deckofcard.domain.Suit;
import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import com.jrichard.logmein.deckofcard.domain.enumeration.ValueEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CardService {

    public void getAllCardOfSuit(SuitEnum suitType, Suit suit) {
        List<ValueEnum> shuffledCard = Lists.newArrayList(ValueEnum.values());
        Collections.shuffle(shuffledCard);
        for (ValueEnum value : shuffledCard) {
            final Card card = new Card();
            card.setValue(value);
            card.setSuitType(suitType);
            suit.addCards(card);
        }
    }

}
