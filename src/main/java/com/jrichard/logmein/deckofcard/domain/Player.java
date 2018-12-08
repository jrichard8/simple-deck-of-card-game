package com.jrichard.logmein.deckofcard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sum_of_card_value")
    private Integer sumOfCardValue;

    @ManyToOne
    @JsonIgnoreProperties("players")
    private Game game;

    @OneToOne    @JoinColumn(unique = true)
    private Hand hand;

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

    public Player name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSumOfCardValue() {
        return sumOfCardValue;
    }

    public Player sumOfCardValue(Integer sumOfCardValue) {
        this.sumOfCardValue = sumOfCardValue;
        return this;
    }

    public void setSumOfCardValue(Integer sumOfCardValue) {
        this.sumOfCardValue = sumOfCardValue;
    }

    public Game getGame() {
        return game;
    }

    public Player game(Game game) {
        this.game = game;
        return this;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Hand getHand() {
        return hand;
    }

    public Player hand(Hand hand) {
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
        Player player = (Player) o;
        if (player.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sumOfCardValue=" + getSumOfCardValue() +
            "}";
    }
}
