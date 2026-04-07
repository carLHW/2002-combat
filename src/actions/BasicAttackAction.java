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
        return user.canAct(null);
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        Combatant Target = target.primaryTarget();
        if (Target.isAlive()){
            int dmg = Math.max(0, user.getAttack() - Target.getDefense());
            Target.receiveDamage(dmg);
        }
    }
}

// do i need to implement print lines
//not sure how to implement canAct