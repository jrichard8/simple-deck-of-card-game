package com.jrichard.logmein.deckofcard.service.dto;

import java.util.HashSet;
import java.util.Set;

public class HandDTO {


    private Set<CardDTO> cards = new HashSet<>();
    private Long id;

    public Set<CardDTO> getCards() {
        return cards;
    }

    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
