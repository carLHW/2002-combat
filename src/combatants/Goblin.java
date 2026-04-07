package combatants;

import java.util.ArrayList;
import java.util.List;
import actions.BasicAttackAction;
import api.Action;
import model.AbstractEnemy;

public final class Goblin extends AbstractEnemy {
    public Goblin(String name) {
        super(name, 55, 35, 15, 25);
    }
    public List<Action> getActions(){
        List<Action> actions = new ArrayList<>();
        actions.add(new BasicAttackAction());
        return actions;
    }
}
