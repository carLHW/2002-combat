package model;

import api.Action;
import api.ActionTarget;
import api.BattleContext;
import api.BattleView;
import api.Team;

// Shared base class for enemy combatants.
// TODO: if enemy behavior becomes more advanced later, put the shared logic here.
public abstract class AbstractEnemy extends AbstractCombatant {
    protected AbstractEnemy(String name, int maxHp, int attack, int defense, int speed) {
        super(name, Team.ENEMY, maxHp, attack, defense, speed);
    }

    @Override
    public Action chooseAction(BattleView battleView) {
        return super.chooseAction(battleView);
    }

    @Override
    public ActionTarget chooseTarget(Action action, BattleView battleView, BattleContext battleContext) {
        return super.chooseTarget(action, battleView, battleContext);
    }
}
