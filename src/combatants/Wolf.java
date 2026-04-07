package combatants;

import actions.BasicAttackAction;
import api.Action;
import model.AbstractEnemy;

public final class Wolf extends AbstractEnemy {
    public Wolf(String name) {
        super(name, 40, 45, 5, 35);
    }
    public Action UseBasicAttack(){
        return new BasicAttackAction();
    }
}
