package level;

import java.util.List;

public final class LevelFactory {
    private LevelFactory() {
    }

    public static LevelSetup createLevel(Difficulty difficulty) {
        // TODO: implement difficulty setup
        return new LevelSetup(List.of(), List.of());
    }
}
