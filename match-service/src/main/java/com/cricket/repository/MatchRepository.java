package com.cricket.repository;

import com.cricket.entity.Match;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Match, Long> {
  @Modifying
  @Transactional
  @Query("""
  update Match m
     set m.availableSeats = m.availableSeats - :count
   where m.id = :id
     and m.availableSeats >= :count
""")
  int tryReserve(@Param("id") Long id,
      @Param("count") int count);


}