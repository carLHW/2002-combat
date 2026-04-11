package level;

import api.Combatant;
import combatants.Goblin;
import combatants.Wolf;
import java.util.ArrayList;
import java.util.List;

// TODO: recheck enemy names and level setup against the final submission wording if needed.
public final class LevelFactory {
    private LevelFactory() {
    }

    public static LevelSetup createLevel(Difficulty difficulty) {
        List<Combatant> initialEnemies = new ArrayList<>();
        List<Combatant> backupEnemies = new ArrayList<>();

        switch (difficulty) {
            case EASY:
                initialEnemies.add(new Goblin("Weak Goblin 1"));
                initialEnemies.add(new Goblin("Weak Goblin 2"));
                initialEnemies.add(new Goblin("Weak Goblin 3"));
                break;
            case MEDIUM:
                initialEnemies.add(new Goblin("Goblin Guard"));
                initialEnemies.add(new Wolf("Wild Wolf"));
                backupEnemies.add(new Wolf("Pack Wolf 1"));
                backupEnemies.add(new Wolf("Pack Wolf 2"));
                break;
            case HARD:
                initialEnemies.add(new Goblin("Goblin Frontliner 1"));
                initialEnemies.add(new Goblin("Goblin Frontliner 2"));
                backupEnemies.add(new Goblin("Goblin Chief"));
                backupEnemies.add(new Wolf("Pack Wolf 1"));
                backupEnemies.add(new Wolf("Pack Wolf 2"));
                break;
        }

        return new LevelSetup(initialEnemies, backupEnemies);
    }
}
