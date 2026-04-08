package engine;

import api.Team;

public record BattleResult(Team winner, int roundsCompleted) {
    // A record automatically creates the constructor, getters, 
    // equals(), hashCode(), and toString() for us!
}