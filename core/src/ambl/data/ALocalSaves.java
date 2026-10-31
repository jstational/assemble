package ambl.data;

import mindustry.world.blocks.logic.LogicBlock.*;

import arc.struct.*;

public final class ALocalSaves {
    private static ObjectMap<LogicBuild, String> saves;

    private ALocalSaves() {}

    public static void init() {
        saves = new ObjectMap<>();
    }

    public static void clear() {
        saves.clear();
    }

    public static  String remove(LogicBuild build) {
        return saves.remove(build);
    }

    public static String get(LogicBuild build) {
        return saves.get(build);
    }

    public static void add(LogicBuild build, String code) {
        saves.put(build, code);
    }
}