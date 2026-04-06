package engine;

import api.BattleContext;
import api.Combatant;
import java.util.List;

public final class DefaultBattleContext implements BattleContext {
    public DefaultBattleContext(BattleEngine battleEngine) {
        // TODO: store battle engine reference
    }

    @Override
    public void log(String message) {
        // TODO: connect logging to engine or CLI
    }

    @Override
    public List<Combatant> getLivingOpponentsOf(Combatant combatant) {
        // TODO: implement battle context
        return List.of();
    }

    @Override
    public List<Combatant> getLivingAlliesOf(Combatant combatant) {
        // TODO: implement battle context
        return List.of();
    }

    @Override
    public void registerDefeat(Combatant target, Combatant defeatedBy) {
        // TODO: implement defeat handling
    }

    @Override
    public int getRoundNumber() {
        // TODO: implement round lookup
        return 0;
    }
}
