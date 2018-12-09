package com.jrichard.logmein.deckofcard.service.dto;

import java.util.List;

public class GameDTO {

    private Long id;

    private String name;

    private List<PlayerDTO> players;

    private List<DeckDTO> decks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<DeckDTO> getDecks() {
        return decks;
    }

    public void setDecks(List<DeckDTO> decks) {
        this.decks = decks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
