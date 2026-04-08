package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;
import java.util.List;

public final class ArcaneBlastAction implements Action {
    @Override
    public String getName() {
        return "ArcaneBlast";
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
        List<Combatant> enemies = target.targets();
        String logMsg = user.getName() + " → Arcane Blast → All Enemies: ";

        for (int i = 0; i < enemies.size(); i++) {
            Combatant enemy = enemies.get(i);
            if (enemy != null && enemy.isAlive()) {
                int oldAtk = user.getAttack();
                int oldHp = enemy.getCurrentHp();
                int dmg = Math.max(0, oldAtk - enemy.getDefense());
                enemy.receiveDamage(dmg);
                int newHp = enemy.getCurrentHp();

                logMsg += enemy.getName() + " HP: " + oldHp + " → " + newHp;

                if (!enemy.isAlive()) {
                    logMsg += " X ELIMINATED";
                    target.context().registerDefeat(enemy, user);
                    user.modifyAttack(10); 
                    logMsg += " (dmg: " + oldAtk + "-" + enemy.getDefense() + "=" + dmg + ") | ATK: " + oldAtk + " → " + user.getAttack() + " (+10)";
                }   
                else {
                logMsg += " (dmg: " + oldAtk + "-" + enemy.getDefense() + "=" + dmg + ")";
                }

                if (i < enemies.size() - 1) logMsg += " | ";
            }
        }
        target.context().log(logMsg);
    }

}
