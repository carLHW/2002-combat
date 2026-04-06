package engine;

import api.Combatant;
import api.Team;
import api.TurnOrderStrategy;
import java.util.List;

public final class BattleEngine {
    public BattleEngine(
            TurnOrderStrategy turnOrderStrategy,
            List<Combatant> initialCombatants,
            List<Combatant> backupSpawn
    ) {
        // TODO: store battle state
    }

    public BattleResult runBattle() {
        // TODO: implement battle loop
        return new BattleResult(Team.ENEMY, 0);
    }

    public int getRoundNumber() {
        return 0;
    }

    public List<Combatant> getLivingCombatants() {
        // TODO: implement living combatants lookup
        return List.of();
    }

    public List<Combatant> getLivingOpponentsOf(Combatant combatant) {
        // TODO: implement opponents lookup
        return List.of();
    }

    public List<Combatant> getLivingAlliesOf(Combatant combatant) {
        // TODO: implement allies lookup
        return List.of();
    }
}
