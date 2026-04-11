package combatants;

import actions.BasicAttackAction;
import actions.DefendAction;
import actions.ShieldBashAction;
import actions.UseItemAction;
import model.AbstractPlayer;

public final class Warrior extends AbstractPlayer {
    public Warrior(String name) {
        super(name, 260, 40, 20, 30);
        addAction(new BasicAttackAction());
        addAction(new DefendAction());
        addAction(new ShieldBashAction());
        addAction(new UseItemAction());
    }
}
