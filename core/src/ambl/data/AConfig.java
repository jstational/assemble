package ambl.data;

import arc.files.Fi;
import arc.util.serialization.*;
import arc.util.ArcRuntimeException;
import arc.struct.ObjectMap;

import ambl.AVars;

/**
 * Manages consistent configuration across sessions, should not be used to store states that change quickly
 */
public final class AConfig {
    private static ObjectMap<String, String> config = new ObjectMap<>;
    private static boolean hasUncommitted = false;

    private AConfig() {}

    public static void init() {}

    public static Fi getConfigDir() {
        return config;
    }

    public static void addConfig(String name, String val) {}
}