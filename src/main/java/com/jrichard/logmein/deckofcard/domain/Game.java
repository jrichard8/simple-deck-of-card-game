package com.jrichard.logmein.deckofcard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "game")
    private Set<Deck> decks = new HashSet<>();
    @OneToMany(mappedBy = "game")
    private Set<Player> players = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Game name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Deck> getDecks() {
        return decks;
    }

    public Game decks(Set<Deck> decks) {
        this.decks = decks;
        return this;
    }

    public Game addDecks(Deck deck) {
        this.decks.add(deck);
        deck.setGame(this);
        return this;
    }

    public Game removeDecks(Deck deck) {
        this.decks.remove(deck);
        deck.setGame(null);
        return this;
    }

    public void setDecks(Set<Deck> decks) {
        this.decks = decks;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Game players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Game addPlayers(Player player) {
        this.players.add(player);
        player.setGame(this);
        return this;
    }

    public Game removePlayers(Player player) {
        this.players.remove(player);
        player.setGame(null);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
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
        Game game = (Game) o;
        if (game.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
