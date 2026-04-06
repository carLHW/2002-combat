package items;

import api.ActionTarget;
import api.BattleView;
import api.Item;
import model.AbstractPlayer;

public final class PowerStoneItem implements Item {
    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public boolean canUse(AbstractPlayer user, BattleView battleView) {
        // TODO: decide whether there are any usage restrictions for Power Stone.
        return true;
    }

    @Override
    public void use(AbstractPlayer user, ActionTarget target) {
        // TODO: implement PowerStoneItem
    }
}
