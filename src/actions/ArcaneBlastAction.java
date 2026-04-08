package actions;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Combatant;
import java.util.List;

public final class ArcaneBlastAction implements Action {
    @Override
    public String getName() {
        return "Arcane Blast";
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
        int kills = 0;
        for (Combatant enemy : enemies){
            if (enemy != null && enemy.isAlive()){
                int dmg = Math.max(0, user.getAttack() - enemy.getDefense());
                enemy.receiveDamage(dmg);

                if (!enemy.isAlive()){
                    kills++;
                    target.context().registerDefeat(enemy, user);
                }
            }
        }
        
        if (kills>0){
            user.modifyAttack(kills*10);
        }

        user.getCooldownTracker().startCooldown(getName(), 3);
    }
}