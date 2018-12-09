package com.jrichard.logmein.deckofcard.repository;

import com.jrichard.logmein.deckofcard.domain.Card;
import com.jrichard.logmein.deckofcard.domain.enumeration.SuitEnum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Card entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByHandIsNull();
    List<Card> findAllBySuitIdAndHandIsNull(Long id);


}
