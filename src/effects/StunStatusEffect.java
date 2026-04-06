package effects;

import api.BattleContext;
import api.Combatant;
import model.AbstractStatusEffect;

public final class StunStatusEffect extends AbstractStatusEffect {
    public StunStatusEffect(int turnsToSkip) {
        super(turnsToSkip);
    }

    @Override
    public String getName() {
        return "Stun";
    }

    @Override
    public boolean preventsAction(Combatant target, BattleContext battleContext) {
        // TODO: implement StunStatusEffect
        return false;
    }
}
