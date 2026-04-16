package items;

import api.ActionTarget;
import api.BattleContext;
import api.BattleView;
import api.Combatant;
import api.Item;
import model.AbstractPlayer;
import model.AbstractStatusEffect;

// move Smoke Bomb damage-nullification into a cleaner shared hook if the team refactors later.
private static final class SmokeBombStatusEffect extends AbstractStatusEffect {
    private static final int DEFENSE_BONUS = 100;

    private SmokeBombStatusEffect() {
        super(2);
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public void onApply(Combatant target, BattleContext battleContext) {
        target.modifyDefense(DEFENSE_BONUS);
    }

    @Override
    public void onRoundEnd(Combatant target, BattleContext battleContext) {
        reduceRoundsByOne();
    }

    @Override
    public void onExpire(Combatant target, BattleContext battleContext) {
        target.modifyDefense(-DEFENSE_BONUS);
    }
}
