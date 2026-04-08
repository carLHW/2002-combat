package engine;

import api.BattleView;
import api.Combatant;
import java.util.List;

public final class DefaultBattleView implements BattleView {
    private final BattleEngine battleEngine;

    public DefaultBattleView(BattleEngine battleEngine) {
        this.battleEngine = battleEngine;
    }

    @Override
    public int getRoundNumber() {
        return battleEngine.getRoundNumber();
    }

    @Override
    public List<Combatant> getLivingCombatants() {
        return battleEngine.getLivingCombatants();
    }

    @Override
    public List<Combatant> getLivingOpponentsOf(Combatant combatant) {
        return battleEngine.getLivingOpponentsOf(combatant);
    }

    @Override
    public List<Combatant> getLivingAlliesOf(Combatant combatant) {
        return battleEngine.getLivingAlliesOf(combatant);
    }
}