package items;

import api.ActionTarget;
import api.BattleView;
import api.Item;
import model.AbstractPlayer;
import api.BattleContext;
import api.Combatant;
import model.AbstractStatusEffect;

public final class SmokeBombItem implements Item {

    private static final int SMOKE_BOMB_DEFENSE_BONUS = 100;
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
        user.addStatusEffect(new SmokeBombStatusEffect(SMOKE_BOMB_DEFENSE_BONUS), battleContext);

        if (battleContext != null) {
            battleContext.log(user.getName() + " used Smoke Bomb.");
        }
    }

    private static final class SmokeBombStatusEffect extends AbstractStatusEffect {
        private final int defenseBonus;

        private SmokeBombStatusEffect(int defenseBonus) {
            super(2);
            this.defenseBonus = defenseBonus;
        }

        @Override
        public String getName() {
            return "Smoke Bomb";
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
}
