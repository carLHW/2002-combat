package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;
import java.util.List;

//TODO: verify whether the isStunned check is implemented correctly
public final class BasicAttackAction implements Action {
    @Override
    public String getName() {
        return "BasicAttack";
    }

    @Override
    public boolean canExecute(Combatant user, BattleView battleView) {
        if (!user.isAlive()) {
            return false;
        }
        boolean isStunned = user.getStatusEffects().stream()
                .anyMatch(effect -> "Stun".equals(effect.getName()));
        if (isStunned) {
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
        boolean blockedBySmokeBomb = enemy.getStatusEffects().stream()
                .anyMatch(effect -> "Smoke Bomb".equals(effect.getName()));
        if (blockedBySmokeBomb) {
            damage = 0;
        }

        enemy.receiveDamage(damage);
        int newHp = enemy.getCurrentHp();
        String logMsg = user.getName() + " → BasicAttack → " + enemy.getName()
                + ": HP: " + oldHp + " → " + newHp;

        if (!enemy.isAlive()) {
            target.context().registerDefeat(enemy, user);
            logMsg += " X Eliminated";
        }
        logMsg += " (dmg: " + user.getAttack() + "-" + enemy.getDefense()
                + "=" + damage + ")";
        target.context().log(logMsg);
    }
}
