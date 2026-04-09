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
        return user.isAlive() && user.canAct(null);
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        int oldDef = user.getDefense();
        user.addStatusEffect(new DefendStatusEffect(10), target.context());
        int newDef = user.getDefense();
        target.context().log(user.getName() + " → Defends, DEF: " + oldDef + " → " + newDef +
        " (+10 for 2 turns)");
    }
}
