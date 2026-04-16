package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;
import java.util.List;

// TODO: recheck the Arcane Blast attack-bonus rule against the final assignment wording.
public final class ArcaneBlastAction implements Action {
    @Override
    public String getName() {
        return "ArcaneBlast";
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
        List<Combatant> enemies = target.targets();
        String logMsg = user.getName() + " → Arcane Blast → All Enemies: ";
        int oldAtk = user.getAttack();

        for (int i = 0; i < enemies.size(); i++) {
            Combatant enemy = enemies.get(i);
            if (enemy != null && enemy.isAlive()) {
                int oldHp = enemy.getCurrentHp();
                int damage = Math.max(0, oldAtk - enemy.getDefense());
                enemy.receiveDamage(damage);
                int newHp = enemy.getCurrentHp();

                logMsg += enemy.getName() + " HP: " + oldHp + " → " + newHp;

                if (!enemy.isAlive()) {
                    logMsg += " X ELIMINATED";
                    target.context().registerDefeat(enemy, user);
                    user.modifyAttack(10);
                    logMsg += " (dmg: " + oldAtk + "-" + enemy.getDefense() + "=" + damage
                            + ") | ATK: " + oldAtk + " → " + user.getAttack() + " (+10)";
                } else {
                    logMsg += " (dmg: " + oldAtk + "-" + enemy.getDefense() + "=" + damage + ")";
                }

                if (i < enemies.size() - 1) {
                    logMsg += " | ";
                }
            }
        }
        user.getCooldownTracker().startCooldown(getName(), 3);
        target.context().log(logMsg);
    }
}
