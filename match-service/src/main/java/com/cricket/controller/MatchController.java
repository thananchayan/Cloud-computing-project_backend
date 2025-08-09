package com.cricket.controller;

import com.cricket.dto.request.MatchRequest;
import com.cricket.dto.response.ContentResponse;
import com.cricket.entity.Match;
import com.cricket.repository.MatchRepository;
import com.cricket.service.MatchService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;
  private final MatchRepository matchRepository;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ContentResponse<Match> createMatch(@RequestBody @Valid MatchRequest match) {
    return matchService.createMatch(match);
  }

  @GetMapping
  public ContentResponse<List<Match>> getAllMatches() {
    return matchService.getAllMatches();
  }

  @GetMapping("/{id}")
  public ContentResponse<Match> getMatchById(@PathVariable Long id) {
    return matchService.getMatchById(id);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ContentResponse<Match> updateMatch(@PathVariable Long id, @RequestBody @Valid MatchRequest match) {
    return matchService.updateMatch(id, match);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ContentResponse<String> deleteMatch(@PathVariable Long id) {
    matchService.deleteMatch(id);
    return new ContentResponse<>(
        "Match",
        null,
        "SUCCESS",
        "200",
        "Match deleted successfully"
    );
  }

  // MatchController.java
  @PutMapping("/{id}/reduce")
  public ResponseEntity<String> reduceSeats(@PathVariable Long id, @RequestParam int count) {
    int updated = matchRepository.tryReserve(id, count);
    if (updated == 0) return ResponseEntity.badRequest().body("Not enough seats available");
    return ResponseEntity.ok("Seats reduced");
  }


}