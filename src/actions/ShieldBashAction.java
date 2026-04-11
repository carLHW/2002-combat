package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;
import effects.StunStatusEffect;
import java.util.List;

// TODO: verify the stun duration and cooldown behavior against the team's final turn flow.
public final class ShieldBashAction implements Action {
    @Override
    public String getName() {
        return "ShieldBash";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
        if (!user.isAlive()) {
            return false;
        }
        if (!user.getCooldownTracker().isReady(getName())) {
            return false;
        }
        List<Combatant> enemies = battleView.getLivingOpponentsOf(user);
        return !enemies.isEmpty();
    }

    @Override
    public void execute(Combatant user, ActionTarget target) {
        Combatant enemy = target.primaryTarget();
        if (enemy == null || !enemy.isAlive()) {
            return;
        }

        int oldHp = enemy.getCurrentHp();
        int damage = Math.max(0, user.getAttack() - enemy.getDefense());
        enemy.receiveDamage(damage);
        int newHp = enemy.getCurrentHp();

        String logMsg = user.getName() + " → Shield Bash → " + enemy.getName()
                + ": HP: " + oldHp + " → " + newHp;

        if (!enemy.isAlive()) {
            target.context().registerDefeat(enemy, user);
            logMsg += " X ELIMINATED";
        } else {
            enemy.addStatusEffect(new StunStatusEffect(2), target.context());
            logMsg += " | " + enemy.getName() + " STUNNED (2 turns)";
        }

        user.getCooldownTracker().startCooldown(getName(), 3);
        logMsg += " (dmg: " + user.getAttack() + "-" + enemy.getDefense() + "="
                + damage + ")";
        target.context().log(logMsg);
    }
}
