package effects;

import api.BattleContext;
import api.Combatant;
import model.AbstractStatusEffect;

public final class DefendStatusEffect extends AbstractStatusEffect {
    private final int defenseBonus;
    public DefendStatusEffect(int defenseBonus) {
        super(2);
        // store defend bonus and duration
        this.defenseBonus = defenseBonus;
    }

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public void onApply(Combatant target, BattleContext battleContext) {
        target.modifyDefense(defenseBonus);
    }

    @Override
    public void onRoundEnd(Combatant target, BattleContext battleContext) {
        reduceRoundsByOne();
    }

    @Override
    public void onExpire(Combatant target, BattleContext battleContext) {
        target.modifyDefense(-defenseBonus);
    }
}
