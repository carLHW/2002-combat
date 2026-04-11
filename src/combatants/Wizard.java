package combatants;

import actions.ArcaneBlastAction;
import actions.BasicAttackAction;
import actions.DefendAction;
import actions.UseItemAction;
import model.AbstractPlayer;

public final class Wizard extends AbstractPlayer {
    public Wizard(String name) {
        super(name, 200, 50, 10, 20);
        addAction(new BasicAttackAction());
        addAction(new DefendAction());
        addAction(new ArcaneBlastAction());
        addAction(new UseItemAction());
    }
}
