package api;

import model.AbstractPlayer;

public interface Item {
    String getName();

    boolean canUse(AbstractPlayer user, BattleView battleView);

    void use(AbstractPlayer user, ActionTarget target);
}

