package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;

public final class ArcaneBlastAction implements Action {
    @Override
    public String getName() {
        return "ArcaneBlast";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
        // TODO: add cooldown check
        return true;
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        // TODO: implement ArcaneBlastAction
    }
}
