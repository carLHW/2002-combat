package level;

import api.Combatant;
import java.util.List;

public record LevelSetup(List<Combatant> initialEnemies, List<Combatant> backupEnemies) {
    // Record automatically handles the constructor and getters for us.
}