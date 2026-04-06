package engine;

import api.Team;

// Small data record for returning battle outcome.
// TODO: keep or refine the result data if the final CLI needs extra statistics.
public record BattleResult(Team winner, int roundsCompleted) {
}
