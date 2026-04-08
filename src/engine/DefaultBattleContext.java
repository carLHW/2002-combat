package engine;

import api.BattleContext;
import api.Combatant;
import java.util.List;

public final class DefaultBattleContext implements BattleContext {
    private final BattleEngine battleEngine;

    public DefaultBattleContext(BattleEngine battleEngine) {
        this.battleEngine = battleEngine;
    }

    @Override
    public void log(String message) {
        // Simple console output for a first-year assignment
        System.out.println(message);
    }

    @Override
    public List<Combatant> getLivingOpponentsOf(Combatant combatant) {
        // Pass the request directly to the engine
        return battleEngine.getLivingOpponentsOf(combatant);
    }

    @Override
    public List<Combatant> getLivingAlliesOf(Combatant combatant) {
        // Pass the request directly to the engine
        return battleEngine.getLivingAlliesOf(combatant);
    }

    @Override
    public void registerDefeat(Combatant target, Combatant defeatedBy) {
        // The engine handles the actual alive/dead states based on HP,
        // so we just use this to announce what happened to the console.
        log(target.getName() + " was defeated by " + defeatedBy.getName() + "!");
    }

    @Override
    public int getRoundNumber() {
        return battleEngine.getRoundNumber();
    }
}