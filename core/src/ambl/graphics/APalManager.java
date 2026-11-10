package ambl.graphics;

import arc.graphics.*;
import arc.util.serialization.*;

import ambl.data.ADataManager;

public final class APalManager {
    private static String desc, name, displayName;

    private static APal pal;

    public static void init() {}

    public static Color getColorByName(String name) {
        if(name == null) return Color.valueOf("000000");
        String namet = name.trim();
        return switch(namet) {
            case "uiLighter" -> pal.uiLighter;
            case "uiLight" -> pal.uiLight;
            case "ui" -> pal.ui;
            case "uiDark" -> pal.uiDark;
            case "uiDarker" -> pal.uiDarker;
            case "uiDarkest" -> pal.uiDarkest;

            case "modified" -> pal.modified;
            case "deleted" -> pal.deleted;
            case "added" -> pal.added;

            case "type" -> pal.type;
            case "callable" -> pal.callable;
            case "var" -> pal.var;
            case "identifier" -> pal.identifier;
            case "comment" -> pal.comment;
            case "number" -> pal.number;
            case "string" -> pal.string;
            case "special" -> pal.special;

            case "depth1" -> pal.depth1;
            case "depth2" -> pal.depth2;
            case "depth3" -> pal.depth3;

            default -> Color.valueOf("000000");
        }
    }

    public static APal getPalette() {
        return pal;
    }

    public static void savePalette(Fi file) {}

    public static void loadPaletteFile(String name) {
        if(name == null) return;
        loadPalette(ADataDirs.palettes.child(name));
    }

    public static void loadPalette(Fi file) {
        if(file == null || !file.exists() || file.isDirectory()) return;
        loadPaletteString(asFile.readString());
    }

    public static void loadPalette(String pal) {
        if(pal == null) return;
        loadPaletteJson(new JsonReader().parse(pal));
    }

    public static void loadPalette(JsonValue json) {}
}