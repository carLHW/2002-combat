package items;

import api.ActionTarget;
import api.BattleView;
import api.Item;
import model.AbstractPlayer;

public final class SmokeBombItem implements Item {
    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public boolean canUse(AbstractPlayer user, BattleView battleView) {
        // TODO: decide whether there are any usage restrictions for Smoke Bomb.
        return true;
    }

    @Override
    public void use(AbstractPlayer user, ActionTarget target) {
        // TODO: implement SmokeBombItem
    }
}
