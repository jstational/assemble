package ambl.graphics;

import arc.graphics.*;
import arc.util.serialization.*;

import ambl.data.ADataDirs;

public final class APalManager {
    public static String desc, name, displayName;

    public static APal pal;

    public static Color getColorByName(String name) {
        if(name == null) return color("000000");
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

            default -> color("000000");
        }
    }

    public static void loadPalette(String pal) {
        if(pal != null) {
            JsonValue readableJson = new JsonReader().parse(pal);
        }
    }

    public static void init() {}

    // helper method
    private static Color color(String col) {
        return Color.valueOf(col);
    }

    public JsonValue getPaletteJSON(String name) {
        Fi p = ADataDirs.palettes.child(name + ".json");
        return p.exists() && !p.isDirectory() ? p : null;
    }

    public Fi getCurrentPaletteFile() {
        Fi p = ADataDirs.config.child("pal.json");
        return p.exists() && !p.isDirectory() ? p : null;
    }
}