package ambl.graphics;

import arc.graphics.*;
import arc.util.serialization.*;

public final class APal {
    public static String paletteDesc, paletteName;

    private static Color
    uiLight2,
    ui2,
    uiDark2,
    uiDarker2,

    classcol,
    callable,
    var,

    comment,

    number,
    string,

    d1,
    d2,
    d3,
    d4,
    d5,

    uiDark1,
    uiDarker1,
    uiDarkest1;

    public static Color getColorByName(String name) {
        if(name == null) return color("000000");
        String namet = name.trim();
        return switch(namet) {
            case "lightTitanium" -> lightTitanium;
            case "titanium" -> titanium;
            case "darkTitanium" -> darkTitanium;
            case "darkerTitanium" -> darkerTitanium;

            case "class" -> classcol;
            case "callable" -> callable;

            case "d1" -> d1;
            case "d2" -> d2;
            case "d3" -> d3;
            case "d4" -> d4;
            case "d5" -> d5;

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