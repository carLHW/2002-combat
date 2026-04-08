package items;

import api.ActionTarget;
import api.BattleView;
import api.Item;
import model.AbstractPlayer;

public final class PotionItem implements Item {
    private final int healAmount;
    public PotionItem(int healAmount) {
        // store heal amount
        this.healAmount = healAmount;
    }

    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public boolean canUse(AbstractPlayer user, BattleView battleView) {
        // check whether Potion can be used
        return user.getCurrentHp() < user.getMaxHp();
    }

    @Override
    public void use(AbstractPlayer user, ActionTarget target) {
        // implement PotionItem
        user.heal(healAmount);
    }
}
