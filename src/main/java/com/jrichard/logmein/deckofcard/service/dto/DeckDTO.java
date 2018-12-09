package com.jrichard.logmein.deckofcard.service.dto;

import java.util.HashSet;
import java.util.Set;

public class DeckDTO {

    private Long id;
    private Set<SuitDTO> suits = new HashSet<>();

    public Set<SuitDTO> getSuits() {
        return suits;
    }

    public void setSuits(Set<SuitDTO> suits) {
        this.suits = suits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
