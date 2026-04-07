package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;
import effects.DefendStatusEffect;

public final class DefendAction implements Action {
    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
        return user.isAlive();
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        user.addStatusEffect(new DefendStatusEffect(10), null);
    }
}
