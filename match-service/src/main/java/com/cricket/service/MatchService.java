package com.cricket.service;

import com.cricket.dto.request.MatchRequest;
import com.cricket.dto.response.ContentResponse;
import com.cricket.entity.Match;
import com.cricket.repository.MatchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;

  public ContentResponse<Match> createMatch(MatchRequest match) {
    Match newMatch = new Match();
    newMatch.setTeamA(match.getTeamA());
    newMatch.setTeamB(match.getTeamB());
    newMatch.setLocation(match.getLocation());
    newMatch.setMatchDate(match.getMatchDate());
    newMatch.setTime(match.getTime());
    newMatch.setFormat(match.getFormat());
    newMatch.setTotalSeats(match.getTotalSeats());
    newMatch.setAvailableSeats(match.getTotalSeats());
    newMatch.setDescription(match.getDescription());
    newMatch.setPrice(match.getPrice());
    newMatch.setImageUrl(match.getImageUrl());

    matchRepository.save(newMatch);
    return new ContentResponse<>(
        "Match",
        newMatch,
        "SUCCESS",
        "201",
        "Match created successfully"
    );
  }

  public ContentResponse<List<Match>> getAllMatches() {
    List<Match> matches = matchRepository.findAll();
    return new ContentResponse<>(
        "Matches",
        matches,
        "SUCCESS",
        "200",
        "Retrieved all matches successfully"
    );
  }

  public ContentResponse<Match> getMatchById(Long id) {
    Match match = matchRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Match not found with id: " + id));
    return new ContentResponse<>(
        "Match",
        match,
        "SUCCESS",
        "200",
        "Retrieved match successfully"
    );
  }

  public ContentResponse<Match> updateMatch(Long id, MatchRequest matchDetails) {
    Match match = matchRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Match not found with id: " + id));
    match.setTeamA(matchDetails.getTeamA());
    match.setTeamB(matchDetails.getTeamB());
    match.setLocation(matchDetails.getLocation());
    match.setMatchDate(matchDetails.getMatchDate());
    match.setTime(matchDetails.getTime());
    match.setPrice(matchDetails.getPrice());
    match.setFormat(matchDetails.getFormat());
    match.setTotalSeats(matchDetails.getTotalSeats());
    match.setImageUrl(matchDetails.getImageUrl());
    match.setAvailableSeats(match.getAvailableSeats());
    match.setDescription(matchDetails.getDescription());
    matchRepository.save(match);
    return new ContentResponse<>(
        "Match",
        match,
        "SUCCESS",
        "200",
        "Match updated successfully"
    );
  }

  public void deleteMatch(Long id) {
    matchRepository.deleteById(id);
  }
}
