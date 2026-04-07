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
        return user.isAlive();
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        Combatant enemy = target.primaryTarget();
        int dmg = Math.max(0, user.getAttack() - enemy.getDefense());
        enemy.receiveDamage(dmg);
        if (!enemy.isAlive()){
            target.context().registerDefeat(enemy, user);
        }
    }
}