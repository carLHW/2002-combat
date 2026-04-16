package items;

import api.ActionTarget;
import api.BattleContext;
import api.BattleView;
import api.Combatant;
import api.Item;
import model.AbstractPlayer;
import model.AbstractStatusEffect;

// move Smoke Bomb damage-nullification into a cleaner shared hook if the team refactors later.
public final class SmokeBombItem implements Item {
    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public boolean canUse(AbstractPlayer user, BattleView battleView) {
        return true;
    }

    @Override
    public void use(AbstractPlayer user, ActionTarget target) {
        BattleContext battleContext = target == null ? null : target.context();
        user.addStatusEffect(new SmokeBombStatusEffect(), battleContext);

        if (battleContext != null) {
            battleContext.log(user.getName() + " used Smoke Bomb.");
        }
    }

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
}
