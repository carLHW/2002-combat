package level;

import api.Combatant;
import java.util.List;

// Small data record for level enemy setup.
// TODO: keep or refine this wrapper if the final level flow needs extra data.
public record LevelSetup(List<Combatant> initialEnemies, List<Combatant> backupEnemies) {
}
