package api;

public interface Action {
    String getName();

    boolean canExecute(Combatant user, BattleView battleView);

    void execute(Combatant user, ActionTarget target);
}

