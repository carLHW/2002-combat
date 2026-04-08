package actions;

import java.util.List;

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
        if (!user.isAlive() || !user.canAct(null)){
            return false;
        }
        List<Combatant> enemies = battleView.getLivingOpponentsOf(user);
        return !enemies.isEmpty();
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        Combatant enemy = target.primaryTarget();
        if (enemy != null && enemy.isAlive()){
            int oldHp = enemy.getCurrentHp();
            int dmg = Math.max(0, user.getAttack() - enemy.getDefense());
            enemy.receiveDamage(dmg);
            int newHp = enemy.getCurrentHp();
            String logMsg = user.getName() + " → BasicAttack → " + enemy.getName() + 
            ": HP: " + oldHp + " → " + newHp;

            if (!enemy.isAlive()){
                target.context().registerDefeat(enemy, user);
                logMsg += " X Eliminated";
            }
            logMsg += " (dmg: " + user.getAttack() + "-" + enemy.getDefense() 
            + "=" + dmg + ")";
            target.context().log(logMsg);
        }
    }
}