package items;

import api.ActionTarget;
import api.BattleView;
import api.Item;
import model.AbstractPlayer;

public final class PotionItem implements Item {
    public PotionItem(int healAmount) {
        // TODO: store heal amount
    }

    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public boolean canUse(AbstractPlayer user, BattleView battleView) {
        // TODO: check whether Potion can be used
        return true;
    }

    @Override
    public void use(AbstractPlayer user, ActionTarget target) {
        // TODO: implement PotionItem
    }
}
