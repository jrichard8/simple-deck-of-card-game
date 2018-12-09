package com.jrichard.logmein.deckofcard.service.dto;

import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import com.jrichard.logmein.deckofcard.domain.enumeration.ValueEnum;

public class CardDTO {

    private SuitEnum suitType;
    private ValueEnum value;
    private HandDTO hand;
    private Long id;

    public SuitEnum getSuitType() {
        return suitType;
    }

    public void setSuitType(SuitEnum suitType) {
        this.suitType = suitType;
    }

    public ValueEnum getValue() {
        return value;
    }

    public void setValue(ValueEnum value) {
        this.value = value;
    }

    public HandDTO getHand() {
        return hand;
    }

    public void setHand(HandDTO hand) {
        this.hand = hand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
