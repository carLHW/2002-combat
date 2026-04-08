package level;

import api.Combatant;
import java.util.ArrayList;
import java.util.List;

// We assume these are in the model package based on your architecture
import model.Goblin;
import model.Wolf;

public final class LevelFactory {
    
    private LevelFactory() {
        // Prevent instantiation of utility class
    }

    public static LevelSetup createLevel(Difficulty difficulty) {
        List<Combatant> initialEnemies = new ArrayList<>();
        List<Combatant> backupEnemies = new ArrayList<>();

        switch (difficulty) {
            case EASY:
                initialEnemies.add(new Goblin("Weak Goblin"));
                // No backups for easy mode
                break;
                
            case MEDIUM:
                initialEnemies.add(new Goblin("Goblin Guard"));
                initialEnemies.add(new Wolf("Wild Wolf"));
                
                // One backup enemy
                backupEnemies.add(new Goblin("Sneaky Goblin")); 
                break;
                
            case HARD:
                initialEnemies.add(new Wolf("Alpha Wolf"));
                initialEnemies.add(new Goblin("Goblin Chief"));
                
                // Two backup enemies
                backupEnemies.add(new Wolf("Pack Wolf"));
                backupEnemies.add(new Goblin("Goblin Shaman"));
                break;
        }

        return new LevelSetup(initialEnemies, backupEnemies);
    }
}