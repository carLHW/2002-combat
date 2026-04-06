package effects;

import api.BattleContext;
import api.Combatant;
import model.AbstractStatusEffect;

public final class DefendStatusEffect extends AbstractStatusEffect {
    public DefendStatusEffect(int defenseBonus) {
        super(0);
        // TODO: store defend bonus and duration
    }

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public void onApply(Combatant target, BattleContext battleContext) {
        // TODO: implement DefendStatusEffect
    }
}
