package com.jrichard.logmein.deckofcard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;

import com.jrichard.logmein.deckofcard.domain.enumeration.ValueEnum;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "suit_type", nullable = false)
    private SuitEnum suitType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_value", nullable = false)
    private ValueEnum value;

    @ManyToOne
    @JsonIgnoreProperties("suits")
    private Suit suit;

    @ManyToOne
    @JsonIgnoreProperties("cards")
    private Hand hand;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuitEnum getSuitType() {
        return suitType;
    }

    public Card suitType(SuitEnum suitType) {
        this.suitType = suitType;
        return this;
    }

    public void setSuitType(SuitEnum suitType) {
        this.suitType = suitType;
    }

    public ValueEnum getValue() {
        return value;
    }

    public Card value(ValueEnum value) {
        this.value = value;
        return this;
    }

    public void setValue(ValueEnum value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Card suit(Suit suit) {
        this.suit = suit;
        return this;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Hand getHand() {
        return hand;
    }

    public Card hand(Hand hand) {
        this.hand = hand;
        return this;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        if (card.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), card.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", suitType='" + getSuitType() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
