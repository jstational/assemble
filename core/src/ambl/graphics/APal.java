package ambl.graphics;

import arc.graphics.*;
import arc.util.serialization.*;

public final class APal {
    public static String paletteDesc, paletteName;

    private static Color
    uiLightest,
    uiLighter,
    uiLight,
    ui,
    uiDark,
    uiDarker,
    uiDarkest,

    modified,
    deleted,
    added,

    type,
    callable,
    var,
    identifier,
    comment,
    number,
    string,
    special,

    depth1,
    depth2,
    depth3;

    public static Color getColorByName(String name) {
        if(name == null) return color("000000");
        String namet = name.trim();
        return switch(namet) {
            case "uiLighter" -> uiLighter;
            case "uiLight" -> uiLight;
            case "ui" -> ui;
            case "uiDark" -> uiDark;
            case "uiDarker" -> uiDarker;
            case "uiDarkest" -> uiDarkest,

            case "modified" -> modified;
            case "deleted" -> deleted;
            case "added" -> added;

            case "type" -> type;
            case "callable" -> callable;
            case "var" -> var;
            case "identifier" -> identifier;
            case "comment" -> comment;
            case "number" -> number;
            case "string" -> string;
            case "special" -> special;

            case "depth1" -> d1;
            case "depth2" -> d2;
            case "depth3" -> d3;

            default -> color("000000");
        };
    }

    public static void loadPalette(String pal) {
        JsonValue readableJson = new JsonReader().parse(pal);
    }

    // helper method
    private static Color color(String col) {
        return Color.valueOf(col);
    }
}