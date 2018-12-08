package com.jrichard.logmein.deckofcard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;

/**
 * A Suit.
 */
@Entity
@Table(name = "suit")
public class Suit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "suit_type", nullable = false)
    private SuitEnum suitType;

    @ManyToOne
    @JsonIgnoreProperties("suits")
    private Deck deck;

    @OneToMany(mappedBy = "suit")
    private Set<Card> suits = new HashSet<>();
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

    public Suit suitType(SuitEnum suitType) {
        this.suitType = suitType;
        return this;
    }

    public void setSuitType(SuitEnum suitType) {
        this.suitType = suitType;
    }

    public Deck getDeck() {
        return deck;
    }

    public Suit deck(Deck deck) {
        this.deck = deck;
        return this;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Set<Card> getSuits() {
        return suits;
    }

    public Suit suits(Set<Card> cards) {
        this.suits = cards;
        return this;
    }

    public Suit addSuits(Card card) {
        this.suits.add(card);
        card.setSuit(this);
        return this;
    }

    public Suit removeSuits(Card card) {
        this.suits.remove(card);
        card.setSuit(null);
        return this;
    }

    public void setSuits(Set<Card> cards) {
        this.suits = cards;
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
        Suit suit = (Suit) o;
        if (suit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Suit{" +
            "id=" + getId() +
            ", suitType='" + getSuitType() + "'" +
            "}";
    }
}
