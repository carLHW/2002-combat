package items;

import api.Action;
import api.ActionTarget;
import api.BattleView;
import api.Item;
import model.AbstractPlayer;

public final class PowerStoneItem implements Item {

    private static final String SPECIAL_SKILL_KEY = "SpecialSkill";
    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public boolean canUse(AbstractPlayer user, BattleView battleView) {
        return user.getActions().stream()
                .anyMatch(action -> SPECIAL_SKILL_KEY.equals(action.getName()));
    }

    @Override
    public void use(AbstractPlayer user, ActionTarget target) {
        Action specialSkill = user.getActions().stream()
                .filter(action -> SPECIAL_SKILL_KEY.equals(action.getName()))
                .findFirst()
                .orElse(null);

        if (specialSkill == null) {
            return;
        }

        int previousCooldown = user.getCooldownTracker().getRemainingTurns(SPECIAL_SKILL_KEY);

        specialSkill.execute(user, target);

        user.getCooldownTracker().startCooldown(SPECIAL_SKILL_KEY, previousCooldown);

        if (target != null && target.context() != null) {
            target.context().log(user.getName() + " activated Power Stone.");
        }
    }
}
