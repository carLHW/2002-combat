package model;

import api.Action;
import api.ActionTarget;
import api.BattleContext;
import api.BattleView;
import api.Team;

// Shared base class for player-controlled combatants.
// TODO: complete player-specific shared behavior in this class.
public abstract class AbstractPlayer extends AbstractCombatant {
    private final Inventory inventory = new Inventory();

    protected AbstractPlayer(String name, int maxHp, int attack, int defense, int speed) {
        super(name, Team.PLAYER, maxHp, attack, defense, speed);
        // TODO: add player-specific default actions
    }

    public final Inventory getInventory() {
        return inventory;
    }

    @Override
    public ActionTarget chooseTarget(Action action, BattleView battleView, BattleContext battleContext) {
        // TODO: implement player-specific target selection
        return super.chooseTarget(action, battleView, battleContext);
    }
}

