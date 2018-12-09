package com.jrichard.logmein.deckofcard.service.mapper;

import com.jrichard.logmein.deckofcard.domain.*;
import com.jrichard.logmein.deckofcard.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper( GameMapper.class );

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "decks", target = "decks"),
        @Mapping(source = "players", target = "players"),
    })
    GameDTO gameToGameDto(Game game);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "sumOfCardValue", target = "sumOfCardValue"),
        @Mapping(source = "hand", target = "hand"),
    })
    PlayerDTO playerToPlayerDto(Player player);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "suits", target = "suits"),
    })
    DeckDTO deckToDeckDto(Deck deck);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "cards", target = "cards"),
    })
    SuitDTO suitToSuitDto(Suit suit);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "suitType", target = "suitType"),
        @Mapping(source = "value", target = "value"),
    })
    CardDTO cardToCardDto(Card card);

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "cards", target = "cards"),
    })
    HandDTO handToHandDTO(Hand hand);


}
