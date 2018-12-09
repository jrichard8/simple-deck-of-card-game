package com.jrichard.logmein.deckofcard.service.dto;

import java.util.Set;

public class SuitDTO {

    private Long id;
    private Set<CardDTO> cards;

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
