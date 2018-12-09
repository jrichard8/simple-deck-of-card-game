package com.jrichard.logmein.deckofcard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Objects;

/**
 * A Deck.
 */
@Entity
@Table(name = "deck")
public class Deck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("decks")
    private Game game;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.REMOVE)
    private Set<Suit> suits = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public Deck game(Game game) {
        this.game = game;
        return this;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Suit> getSuits() {
        return suits;
    }

    public Suit getSuits(SuitEnum suitType) {
        Optional<Suit> any = suits.stream().filter(suit -> suit.getSuitType() == suitType).findAny();
        return any.get();
    }

    public Deck suits(Set<Suit> suits) {
        this.suits = suits;
        return this;
    }

    public Deck addSuits(Suit suit) {
        this.suits.add(suit);
        suit.setDeck(this);
        return this;
    }

    public Deck removeSuits(Suit suit) {
        this.suits.remove(suit);
        suit.setDeck(null);
        return this;
    }

    public void setSuits(Set<Suit> suits) {
        this.suits = suits;
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
        Deck deck = (Deck) o;
        if (deck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Deck{" +
            "id=" + getId() +
            "}";
    }
}
