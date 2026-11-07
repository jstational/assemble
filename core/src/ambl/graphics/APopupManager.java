package ambl.graphics;

import arc.*;
import arc.struct.ObjectMap;
import arc.func.Func;

public final class APopupManager {
    private static APopup current;

    public static void warning(String text, float time) {
        closeCurrent();
        current = new APopup(/* */ null, text);
    }

    public static void warning(String text, float time, ObjectMap<String, Func> buttons) {}

    public static void error(String text, float time) {
        closeCurrent();
        current = new APopup(/* */ null, text);
    }

    public static void error(String text, float time, ObjectMap<String, Func> buttons) {}

    public static void info(String text, float time) {
        closeCurrent();
        current = new APopup(/* */ null, text);
    }

    public static void info(String text, float time, ObjectMap<String, Func> buttons) {}

    public static void closeCurrent() {
        if(current == null) return;
        current.destroy();
    }
}