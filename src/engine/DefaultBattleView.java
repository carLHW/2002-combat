package engine;

import api.BattleView;
import api.Combatant;
import java.util.List;

public final class DefaultBattleView implements BattleView {
    public DefaultBattleView(BattleEngine battleEngine) {
        // TODO: store battle engine reference
    }

    @Override
    public int getRoundNumber() {
        // TODO: implement battle view
        return 0;
    }

    @Override
    public List<Combatant> getLivingCombatants() {
        // TODO: implement battle view
        return List.of();
    }

    @Override
    public List<Combatant> getLivingOpponentsOf(Combatant combatant) {
        // TODO: implement battle view
        return List.of();
    }

    @Override
    public List<Combatant> getLivingAlliesOf(Combatant combatant) {
        // TODO: implement battle view
        return List.of();
    }
}
