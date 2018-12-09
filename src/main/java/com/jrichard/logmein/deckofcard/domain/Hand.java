package com.jrichard.logmein.deckofcard.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Hand.
 */
@Entity
@Table(name = "hand")
public class Hand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "hand", cascade = CascadeType.REMOVE)
    private Set<Card> cards = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public Hand cards(Set<Card> cards) {
        this.cards = cards;
        return this;
    }

    public Hand addCards(Card card) {
        this.cards.add(card);
        card.setHand(this);
        return this;
    }

    public Hand removeCards(Card card) {
        this.cards.remove(card);
        card.setHand(null);
        return this;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
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
        Hand hand = (Hand) o;
        if (hand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Hand{" +
            "id=" + getId() +
            "}";
    }
}
