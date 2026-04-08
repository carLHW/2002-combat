package actions;

import java.util.List;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;
import effects.StunStatusEffect;

public final class ShieldBashAction implements Action {
    @Override
    public String getName() {
        return "ShieldBash";
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
        if (enemy != null && enemy.isAlive()) {
            int oldHp = enemy.getCurrentHp();
            int dmg = Math.max(0, user.getAttack() - enemy.getDefense());
            enemy.receiveDamage(dmg);
            int newHp = enemy.getCurrentHp();

            String logMsg = user.getName() + " → Shield Bash → " + enemy.getName() + 
            ": HP: " + oldHp + " → " + newHp;

            if (!enemy.isAlive()){
                target.context().registerDefeat(enemy, user);
                logMsg += " X ELIMINATED";
            }
            
            else {
                enemy.addStatusEffect(new StunStatusEffect(2), target.context());
            }

            logMsg += " (dmg: " + user.getAttack() + "-" + enemy.getDefense() + "=" + 
            dmg + ")";
            if (enemy.isAlive()){
                logMsg += " | " + enemy.getName() + " STUNNED (2 turns)";
            }
            target.context().log(logMsg);
        }
    }
}
