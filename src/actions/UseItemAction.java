package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;

public final class UseItemAction implements Action {
    @Override
    public String getName() {
        return "UseItem";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
        // TODO: check whether the player has a usable item
        return true;
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        // TODO: implement UseItemAction
    }
}
