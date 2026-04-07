package combatants;

import java.util.ArrayList;
import java.util.List;
import actions.ArcaneBlastAction;
import actions.BasicAttackAction;
import actions.DefendAction;
import actions.UseItemAction;
import api.Action;
import model.AbstractPlayer;

public final class Wizard extends AbstractPlayer {
    public Wizard(String name) {
        super(name, 200, 50, 10, 20);
    }
    public List<Action> getActions(){
        List<Action> actions = new ArrayList<>();
        actions.add(new BasicAttackAction());
        actions.add(new DefendAction());
        actions.add(new ArcaneBlastAction());
        actions.add(new UseItemAction());
        return actions;
    }
}
