package com.jrichard.logmein.deckofcard.repository;

import com.jrichard.logmein.deckofcard.domain.Card;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Card entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

}
