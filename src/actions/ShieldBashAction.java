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

            System.out.print(user.getName() + " → Shield Bash → " + enemy.getName() + 
            ": HP: " + oldHp + " → " + newHp);

            if (!enemy.isAlive()){
                target.context().registerDefeat(enemy, user);
                System.out.print(" X ELIMINATED");
            }
            
            else {
                enemy.addStatusEffect(new StunStatusEffect(2), target.context());
            }

            System.out.print(" (dmg: " + user.getAttack() + "-" + enemy.getDefense() + "=" + 
            dmg + ")");
            if (enemy.isAlive()){
                System.out.print(" | " + enemy.getName() + " STUNNED (2 turns)");
            }
        }
        System.out.println();
    }
}
