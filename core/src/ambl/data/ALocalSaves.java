package ambl.data;

import arc.struct.*;
import mindustry.world.blocks.logic.LogicBlock.*;

public final class ALocalSaves {
    private static HashMap<LogicBuild, String> saves;

    private ALocalSaves() {}

    public void clear() {
        saves.clear();
    }

    public String remove(LogicBuild build) {
        return saves.remove(build);
    }

    public String get(LogicBuild build) {
        return saves.get(build);
    }

    public void add(LogicBuild build, String code) {
        saves.put(build, code);
    }
}