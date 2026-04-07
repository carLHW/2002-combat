package combatants;

import actions.BasicAttackAction;
import api.Action;
import model.AbstractEnemy;

public final class Goblin extends AbstractEnemy {
    public Goblin(String name) {
        super(name, 55, 35, 15, 25);
    }

    public Action UseBasicAttack(){
        return new BasicAttackAction();
    }
}
