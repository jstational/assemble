package ambl.graphics;

import arc.Core;
import arc.struct.ObjectMap;
import arc.func.Func;

public final class PopupManager {
    private static Popup current;

    public static void warning(String msg, float time, Cons<Popup> create,  ObjectMap<String, Cons<Popup>> buttons) {}

    public static void warning(String msg, float time, ObjectMap<String, Cons<Popup>> buttons) {
        warning(msg, time, null, buttons);
    }

    public static void warning(String msg, float time, Cons<Popup> create) {
        warning(msg, time, create, null);
    }

    public static void warning(String msg, float time) {
        warning(msg, time, null);
    }

    public static void warning(String msg) {
        warning(msg, 1f);
    }


    public static void error(String msg, float time, Cons<Popup> create, ObjectMap<String, Cons<Popup>> buttons) {}

    public static void error(String msg, float time, ObjectMap<String, Cons<Popup>> buttons) {
        error(msg, time, null, buttons);
    }

    public static void error(String msg, float time, Cons<Popup> create) {
        error(msg, time, create, null);
    }

    public static void error(String msg, float time) {
        error(msg, time, null);
    }

    public static void error(String msg) {
        error(msg, 1f);
    }


    public static void info(String msg, float time, Cons<Popup> create, ObjectMap<String, Cons<Popup>> buttons) {}

    public static void info(String msg, float time, ObjectMap<String, Cons<Popup>> buttons) {
        info(msg, time, null, buttons);
    }

    public static void info(String msg, float time, Cons<Popup> create) {
        info(msg, time, create, null);
    }

    public static void info(String msg, float time) {
        info(msg, time, null);
    }

    public static void info(String msg) {
        info(msg, 1f);
    }


    public static void popup(Popup popup, float time) {}

    public static void popup(Popup popup) {
        popup(popup, 1f);
    }

    public static void closeCurrent() {
        if(current != null) current.destroy();
    }
}