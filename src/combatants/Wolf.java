package combatants;

import actions.BasicAttackAction;
import model.AbstractEnemy;

public final class Wolf extends AbstractEnemy {
    public Wolf(String name) {
        super(name, 40, 45, 5, 35);
        addAction(new BasicAttackAction());
    }
}
