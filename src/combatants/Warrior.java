package combatants;

import model.AbstractPlayer;
import java.util.ArrayList;
import java.util.List;
import actions.BasicAttackAction;
import actions.DefendAction;
import actions.ShieldBashAction;
import actions.UseItemAction;
import api.Action;

public final class Warrior extends AbstractPlayer {
    public Warrior(String name) {
        super(name, 260, 40, 20, 30);
    }

    public List<Action> getActions(){
        List<Action> actions = new ArrayList<>();
        actions.add(new BasicAttackAction());
        actions.add(new DefendAction());
        actions.add(new ShieldBashAction());
        actions.add(new UseItemAction());
        return actions;
    }

}
