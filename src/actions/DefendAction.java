package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;

public final class DefendAction implements Action {
    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
        return true;
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        // TODO: implement DefendAction
    }
}
