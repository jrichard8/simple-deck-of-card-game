package com.jrichard.logmein.deckofcard.repository;

import com.jrichard.logmein.deckofcard.domain.Suit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Suit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuitRepository extends JpaRepository<Suit, Long> {

}
