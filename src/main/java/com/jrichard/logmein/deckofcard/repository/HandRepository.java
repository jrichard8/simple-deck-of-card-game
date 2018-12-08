package com.jrichard.logmein.deckofcard.repository;

import com.jrichard.logmein.deckofcard.domain.Hand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HandRepository extends JpaRepository<Hand, Long> {

}
