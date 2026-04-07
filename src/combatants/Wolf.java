package combatants;

import java.util.ArrayList;
import java.util.List;
import actions.BasicAttackAction;
import api.Action;
import model.AbstractEnemy;

public final class Wolf extends AbstractEnemy {
    public Wolf(String name) {
        super(name, 40, 45, 5, 35);
    }
    public List<Action> getActions(){
        List<Action> actions = new ArrayList<>();
        actions.add(new BasicAttackAction());
        return actions;
    }
}
