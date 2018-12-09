package com.jrichard.logmein.deckofcard.service.dto;

public class PlayerDTO {

    private Long id;
    private String name;
    private Integer sumOfCardValue;
    private HandDTO hand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSumOfCardValue() {
        return sumOfCardValue;
    }

    public void setSumOfCardValue(Integer sumOfCardValue) {
        this.sumOfCardValue = sumOfCardValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HandDTO getHand() {
        return hand;
    }

    public void setHand(HandDTO hand) {
        this.hand = hand;
    }
}
