package com.jrichard.logmein.deckofcard.repository;

import com.jrichard.logmein.deckofcard.domain.Suit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Suit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuitRepository extends JpaRepository<Suit, Long> {

    List<Suit> findAllByDeckId(Long id);

}
