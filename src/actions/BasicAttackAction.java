package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;

public final class BasicAttackAction implements Action {
    @Override
    public String getName() {
        return "BasicAttack";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
        // TODO: decide when BasicAttack can be used
        return true;
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        // TODO: implement BasicAttackAction
    }
}
